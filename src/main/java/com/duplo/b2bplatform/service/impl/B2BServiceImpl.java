package com.duplo.b2bplatform.service.impl;

import com.duplo.b2bplatform.config.B2BConfig;
import com.duplo.b2bplatform.dataaccess.BusinessRepository;
import com.duplo.b2bplatform.dataaccess.DepartmentRepository;
import com.duplo.b2bplatform.dataaccess.PurchaseOrderRepository;
import com.duplo.b2bplatform.dataaccess.UserRepository;
import com.duplo.b2bplatform.datatransfer.*;
import com.duplo.b2bplatform.domain.onboarding.*;
import com.duplo.b2bplatform.domain.order.OrderStatus;
import com.duplo.b2bplatform.domain.order.PurchaseOrder;
import com.duplo.b2bplatform.service.B2BService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Slf4j
@RequiredArgsConstructor
@Service
public class B2BServiceImpl implements B2BService {

    private final BusinessRepository businessRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final B2BConfig b2BConfig;
    private com.duplo.b2bplatform.client.WebClient cl;
    private final WebClient.Builder wBuilder;

    @Override
    public GenericResponse<PurchaseOrderRecord> createPurchaseOrder(Integer departmentId,PurchaseOrderRecord orderRecord) {
        Department foundDepartment = getDepartment(departmentId);

        var totalPrice = BigDecimal.valueOf(Double.parseDouble(orderRecord.itemUnitPrice())).multiply(BigDecimal.valueOf(Double.parseDouble(orderRecord.orderQuantity())));
        var purchaseOrder = new PurchaseOrder(orderRecord.itemName(), orderRecord.itemDescription(), UUID.randomUUID(),
                OrderStatus.REQUESTED, Integer.parseInt(orderRecord.orderQuantity()), BigDecimal.valueOf(Double.parseDouble(orderRecord.itemUnitPrice())), totalPrice);
        foundDepartment.addDepartmentOrder(purchaseOrder);
        foundDepartment = departmentRepository.save(foundDepartment);

        var pRecord = new PurchaseOrderRecord(purchaseOrder.getItemName(), purchaseOrder.getItemDescription(), String.valueOf(purchaseOrder.getItemUnitPrice()),
                String.valueOf(purchaseOrder.getOrderQuantity()), purchaseOrder.getDepartment().getDepartmentHead().getEmail(), orderRecord.totalOrderPrice(),
                String.valueOf(purchaseOrder.getOrderStatus()),foundDepartment.getBusiness().getBusinessId().toString(), String.valueOf(purchaseOrder.getId()));
        var requestResponse = new GenericResponse<PurchaseOrderRecord>();
        requestResponse.setData(pRecord);
        requestResponse.setMessage(String.format("Order from %s department of %s was successful requested", foundDepartment.getDepartmentName(),
                foundDepartment.getBusiness().getBusinessName()));

        Mono<String> taxResponse = logPurchaseOrderToTaxOffice(purchaseOrder);
        log.info(String.format("Tax log response for %s",taxResponse.toString()));

        return requestResponse;
    }
    private Mono<String> logPurchaseOrderToTaxOffice(PurchaseOrder purchaseOrder){
        var taxLog = new TaxLog(purchaseOrder.getOrderId().toString(), b2BConfig.getPlatformCode(), purchaseOrder.getTotalPrice().toString());
        return cl.getWebClient(wBuilder).post()
                .uri(b2BConfig.getTaxLogApiUrl())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(taxLog), TaxLog.class)
                .retrieve()
                .bodyToMono(String.class);
    }
    @Override
    public GenericResponse<BusinessRecord> openBusinessAccount(BusinessRecord businessRecord) {
        var business = new Business();
        business.setBusinessId(UUID.randomUUID());
        business.setBusinessName(businessRecord.businessName());
        business.setBusinessEmail(businessRecord.businessEmail());

        var staff = new User();
        staff.setEmail(businessRecord.email());
        staff.setFirstName(businessRecord.firstName());
        staff.setLastName(businessRecord.lastName());
        staff.setMobilePhone(businessRecord.phoneNumber());
        staff.setEmploymentType(EmploymentType.OWNER);

        var department = new Department();
        department.setDepartmentName(businessRecord.departmentName());
        department.setDepartmentDescription(businessRecord.departmentDescription());

        business.addUserToBusiness(staff);
        business.addDepartmentToBusiness(department);
        business = businessRepository.save(business);

        var requestResponse = new GenericResponse<BusinessRecord>();
        var bRecord = new BusinessRecord(business.getBusinessName(), business.getBusinessEmail(), staff.getFirstName(),
                staff.getLastName(),staff.getEmail(),staff.getMobilePhone(), department.getDepartmentName(),
                businessRecord.departmentDescription(), business.getId(), String.valueOf(business.getBusinessId()),"");
        requestResponse.setData(bRecord);
        requestResponse.setMessage(String.format("%s business account was successfully created", business.getBusinessName()));
        return requestResponse;
    }
    @Override
    public GenericResponse<UserRecord> addStaffToABusiness(Integer id, UserRecord userRecord) {
        var queriedBusiness = getByEntityId(id);
        var foundBusiness = queriedBusiness.orElseGet(queriedBusiness::orElseThrow);

        var newStaff = new User(Title.Mr, userRecord.email(), userRecord.firstName(), userRecord.lastName(), userRecord.phoneNumber(),
                EmploymentType.STAFF);
        foundBusiness.addUserToBusiness(newStaff);
        foundBusiness = businessRepository.save(foundBusiness);

        var requestResponse = new GenericResponse<UserRecord>();
        var uRecord = new UserRecord(newStaff.getTitle().toString(), newStaff.getEmail(), newStaff.getFirstName(), newStaff.getLastName(),
                newStaff.getMobilePhone(), newStaff.getEmploymentType().toString(), foundBusiness.getId(), foundBusiness.getBusinessId().toString(),
                "", "");
        requestResponse.setData(uRecord);
        requestResponse.setMessage(String.format("%s %s has been created as a staff of %s business account", newStaff.getFirstName(), newStaff.getLastName(),
                foundBusiness.getBusinessName()));
        return requestResponse;
    }
    @Override
    public GenericResponse<DepartmentRecord> createDepartmentForABusiness(Integer id, DepartmentRecord departmentRecord) {
        var queriedBusiness = getByEntityId(id);
        var foundBusiness = queriedBusiness.orElseGet(queriedBusiness::orElseThrow);

        var newDepartment = new Department(departmentRecord.departmentName(), departmentRecord.departmentEmail(),
                departmentRecord.description());
        foundBusiness.addDepartmentToBusiness(newDepartment);
        foundBusiness = businessRepository.save(foundBusiness);

        var requestResponse = new GenericResponse<DepartmentRecord>();
        var dRecord = new DepartmentRecord(newDepartment.getDepartmentName(), newDepartment.getDepartmentDescription(),
                newDepartment.getDepartmentEmail(), String.valueOf(foundBusiness.getBusinessId()), foundBusiness.getId(),
                "");

        requestResponse.setData(dRecord);
        requestResponse.setMessage(String.format("%s has been successfully created under %s business account",
                newDepartment.getDepartmentName(), foundBusiness.getBusinessName()));
        return requestResponse;
    }
    @Override
    public Optional<Business> getByEntityId(Integer id) {
        return businessRepository.findById(id);
    }

    @Override
    public Optional<Business> getByBusinessId(String businessId) {
        var bId = UUID.fromString(businessId);
        return businessRepository.findBusinessByBusinessId(bId);
    }
    private Optional<Department> getDepartmentById(Integer id){
        return departmentRepository.findById(id);
    }

    private Optional<User> getUserById(Integer id){
        return userRepository.findById(id);
    }

    private Optional<PurchaseOrder> getPurchaseOrderById(Integer id){
        return purchaseOrderRepository.findById(id);
    }
    private List<PurchaseOrder> getPurchaseOrdersByDepartment(Department foundDepartment) {
        return purchaseOrderRepository.findPurchaseOrderByDepartment(foundDepartment);
    }
    @Override
    public GenericResponse<DepartmentRecord> addStaffToABusinessDepartment(Integer departmentId, Integer userId) {
        Department foundDepartment = getDepartment(departmentId);

        var queriedUser = getUserById(userId);
        var foundUser = queriedUser.orElseGet(queriedUser::orElseThrow);

        foundDepartment.addUserToDepartment(foundUser);
        foundDepartment.setDepartmentHead(foundUser);
        foundDepartment = departmentRepository.save(foundDepartment);

        var dRecord = new DepartmentRecord(foundDepartment.getDepartmentName(), foundDepartment.getDepartmentDescription(),
                foundDepartment.getDepartmentEmail(), String.valueOf(foundDepartment.getBusiness().getBusinessId()),
                foundDepartment.getBusiness().getId(), String.valueOf(foundDepartment.getId()));

        var queryResponse = new GenericResponse<DepartmentRecord>();
        queryResponse.setData(dRecord);
        queryResponse.setMessage(String.format("%s%s was successfully added to %s for %s business account", foundUser.getFirstName(),
                foundUser.getLastName(), foundDepartment.getDepartmentName(), foundDepartment.getBusiness().getBusinessName()));
        return queryResponse;
    }
    @Override
    public GenericResponse<String> calculateCreditScore(Integer departmentId) {
        Department foundDepartment = getDepartment(departmentId);
        List<PurchaseOrder> deptOrders = getPurchaseOrdersByDepartment(foundDepartment);

        var transactionsBy100 = BigDecimal.valueOf((long) deptOrders.size() * 100);
        var totalAmount = BigDecimal.valueOf(deptOrders.stream().filter(o -> o.getTotalPrice().doubleValue() > 0)
                .mapToDouble(o -> o.getTotalPrice().doubleValue()).sum());
        var creditScore = totalAmount.divide(transactionsBy100, 3, RoundingMode.HALF_UP);

        var queryResponse = new GenericResponse<String>();
        queryResponse.setData(String.valueOf(creditScore));
        queryResponse.setMessage(String.format("Credit score for %s is", foundDepartment.getBusiness().getBusinessName()));
        return queryResponse;
    }
    private Department getDepartment(Integer departmentId) {
        var queriedDepartment = getDepartmentById(departmentId);
        return queriedDepartment.orElseGet(queriedDepartment::orElseThrow);
    }
    @Override
    public GenericResponse<PurchaseOrderSummary> getDepartmentPurchaseOrderReport(Integer departmentId){
        Department foundDepartment = getDepartment(departmentId);
        List<PurchaseOrder> departmentOrders = getPurchaseOrdersByDepartment(foundDepartment);

        var totalOrdersCount = departmentOrders.size();
        var totalOrderAmount = BigDecimal.valueOf(departmentOrders.stream().filter(o -> o.getTotalPrice().doubleValue() > 0)
                .mapToDouble(o -> o.getTotalPrice().doubleValue()).sum());
        var totalOrdersAmountToday  = departmentOrders.stream()
                .filter(this::isRecentTransaction)
                .map(PurchaseOrder::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        var totalOrdersCountToday = departmentOrders.stream()
                .filter(this::isRecentTransaction).count();

        var purchaseOrderSummary = new PurchaseOrderSummary(String.valueOf(totalOrdersCount),String.valueOf(totalOrderAmount),
                String.valueOf(totalOrdersCountToday),String.valueOf(totalOrdersAmountToday));

        var queryResponse = new GenericResponse<PurchaseOrderSummary>();
        queryResponse.setData(purchaseOrderSummary);
        queryResponse.setMessage(String.format("Purchase order report for %s", foundDepartment.getBusiness().getBusinessName()));
        return queryResponse;
   }

    private boolean isRecentTransaction(PurchaseOrder purchaseOrder) {
        String date = purchaseOrder.getDateCreated().toString().split(" ")[0];
        return LocalDate.now().isEqual(LocalDate.parse(date));
    }

    @Override
    public GenericResponse<DepartmentRecord> addStaffToABusinessDepartment(Integer departmentId, UserRecord userRecord) {
        return null;
    }

}
