package uz.uzkassa.smartposrestaurant.constants;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.10.2022 15:41
 */
public interface ApiConstants {
    String api = "/api";
    String admin = "/admin";
    String cabinet = "/cabinet";
    String pub = "/public";

    String version = "/v1";

    String adminRootApi = api + admin + version;
    String cabinetRootApi = api + cabinet + version;
    String publicRootApi = api + pub + version;

    String companyApi = "/companies";
    String bankApi = "/banks";
    String branchApi = "/branches";
    String departmentApi = "/departments";
    String id = "/{id}";
    String lookUp = "/lookup";
    String activityTypeApi = "/activity-types";
    String sync = "/sync";
    String statuses = "/statuses";
    String districtApi = "/districts";
    String updateStatus = "/{id}/status";
    String employeeApi = "/employees";
    String roles = "/roles";
    String postApi = "/posts";
    String productApi = "/products";
    String unitApi = "/units";
    String items = "/items";
    String receiptApi = "/receipts";
    String regionApi = "/regions";
    String categoryApi = "/categories";
    String permissionApi = "/permissions";
    String authenticateApi = "/authenticate";
    String accountApi = "/account";
    String all = "/all";
    String check = "/check";
    String verify = "/verify";
    String register = "/register";
    String profile = "/profile";
    String login = "/login";
    String logout = "/logout";
    String ctsApi = "/cts";
    String cacheApi = "/caches";
    String orderApi = "/orders";
    String reservationApi = "/reservations";
    String updateNumber = "/{id}/number";
    String resetPasswordInit = "/reset-password/init";
    String resetPasswordCheck = "/reset-password/check";
    String resetPasswordFinish = "/reset-password/finish";
    String resendResetPasswordKey = "/resend-reset-password-key";
    String resendActivationKey = "/resend-activation-key";
    String changePasswordInit = "/change-password/init";
    String changePasswordConfirm = "/change-password-confirm";
    String changeLogin = "/change-login";
    String changeLoginConfirm = "/change-login-confirm";

    //Swagger
    String swaggerRootApi = api;

    String adminActivityTypeRootApi = adminRootApi + activityTypeApi;
    String publicActivityTypeRootApi = publicRootApi + activityTypeApi;

    String adminBankRootApi = adminRootApi + bankApi;
    String cabinetBankRootApi = cabinetRootApi + bankApi;


    String adminReceiptRooApi = adminRootApi + receiptApi;
    String adminPostRootApi = adminRootApi + postApi;
    String adminCtsRootApi = adminRootApi + ctsApi;
    String adminPermissionRootApi = adminRootApi + permissionApi;
    String adminCacheRootApi = adminRootApi + cacheApi;

    String cabinetAccountRootApi = cabinetRootApi + accountApi;
    String cabinetOrderRootApi = cabinetRootApi + orderApi;
    String cabinetReservationRootApi = cabinetRootApi + reservationApi;
    String cabinetPostRootApi = cabinetRootApi + postApi;




    String adminCategoryRootApi = adminRootApi + categoryApi;
    String cabinetCategoryRootApi = cabinetRootApi + categoryApi;

    String adminEmployeeRootApi = adminRootApi + employeeApi;
    String cabinetEmployeeRootApi = cabinetRootApi + employeeApi;

    String adminCompanyRootApi = adminRootApi + companyApi;
    String cabinetCompanyRootApi = cabinetRootApi + companyApi;

    String adminBranchRootApi = adminRootApi + branchApi;
    String cabinetBranchRootApi = cabinetRootApi + branchApi;

    String adminDepartmentRootApi = adminRootApi + departmentApi;
    String cabinetDepartmentRootApi = cabinetRootApi + departmentApi;

    String adminProductRootApi = adminRootApi + productApi;
    String cabinetProductRootApi = cabinetRootApi + productApi;

    //Public
    String publicUnitRootApi = publicRootApi + unitApi;
    String publicRegionRootApi = publicRootApi + regionApi;
    String publicDistrictRootApi = publicRootApi + districtApi;
    String publicAccountRootApi = publicRootApi;
}
