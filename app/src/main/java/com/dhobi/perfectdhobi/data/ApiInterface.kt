package com.dhobi.perfectdhobi.data

import com.dhobi.perfectdhobi.data.model.AddAddressModel.AddAddressRequestModel
import com.dhobi.perfectdhobi.data.model.AddAddressModel.AddAddressResponseModel.AddAddressResponseModel
import com.dhobi.perfectdhobi.data.model.BannerModel.BannerResponseModel
import com.dhobi.perfectdhobi.data.model.BookedSlotModel.BookedSlotModelResponse.BookedSlotModelResponse
import com.dhobi.perfectdhobi.data.model.CommonResponseModel
import com.dhobi.perfectdhobi.data.model.DeleteAddressModel.DeleteAddressRequestModel
import com.dhobi.perfectdhobi.data.model.EditAddressModel.EditAddressRequestModel.EditAddressRequestModel
import com.dhobi.perfectdhobi.data.model.EditAddressModel.EditAddressResponseModel
import com.dhobi.perfectdhobi.data.model.FAQmodel.FAQResponseModel
import com.dhobi.perfectdhobi.data.model.HelpandSupportModel.HelpandSupportResponseModel
import com.dhobi.perfectdhobi.data.model.menu_and_addon_model.MenuAddOnSendRequestModel
import com.dhobi.perfectdhobi.data.model.menu_and_addon_model.MenuandAddonResponseModel
import com.dhobi.perfectdhobi.data.model.menu_and_addon_model.send_menu_item_response.MenuTempItemCreateResponseModel
import com.dhobi.perfectdhobi.data.model.PrimaryAddressSetModel.PrimaryAddressRequestModel
import com.dhobi.perfectdhobi.data.model.ProfileGetResponseModel.ProfileGetResponseModel
import com.dhobi.perfectdhobi.data.model.ProfilePictureModel.ProfilePicResponseModel
import com.dhobi.perfectdhobi.data.model.ProfileUpdateModel.ProfileUpdateRequestModel
import com.dhobi.perfectdhobi.data.model.ProfileUpdateModel.ProfileUpdateResponseModel.ProfileUpdateResponseModel
import com.dhobi.perfectdhobi.data.model.RatechartModel.RatechartResponseModel
import com.dhobi.perfectdhobi.data.model.SlotBookingModel.SlotBookingRequestModel
import com.dhobi.perfectdhobi.data.model.SlotBookingModel.SlotBookingResponseModel.SlotBookingResponseModel
import com.dhobi.perfectdhobi.data.model.UpdateAddressModel.UpdateAddressRequestModel
import com.dhobi.perfectdhobi.data.model.UpdateAddressModel.UpdateAddressResponseModel.UpdateAddressResponseModel
import com.dhobi.perfectdhobi.data.model.add_to_wallet.RechargeWalletFailedRequestModel
import com.dhobi.perfectdhobi.data.model.add_to_wallet.RechargeWalletRequestModel
import com.dhobi.perfectdhobi.data.model.add_to_wallet.RechargeWalletVerifiedRequestModel
import com.dhobi.perfectdhobi.data.model.add_to_wallet.recharge_wallet_response.RechargeWalletResponseModel
import com.dhobi.perfectdhobi.data.model.address.AddressResponseModel
import com.dhobi.perfectdhobi.data.model.expriedotp.ExpriedOtpRequestModel
import com.dhobi.perfectdhobi.data.model.expriedotp.ExpriedOtpResponseModel.ExpriedOtpResponseModel
import com.dhobi.perfectdhobi.data.model.get_wallet_response.WalletResponseModel
import com.dhobi.perfectdhobi.data.model.loginmodel.LoginRequestModel
import com.dhobi.perfectdhobi.data.model.loginmodel.LoginResponseModel.LoginResponseModel
import com.dhobi.perfectdhobi.data.model.logoutmodel.LogoutResponseModel.LogoutResponseModel
import com.dhobi.perfectdhobi.data.model.menu_and_addon_model.TempOrderSummeryRequestModel
import com.dhobi.perfectdhobi.data.model.menu_and_addon_model.temp_order_summery.TempOrderSummeryResponseModel
import com.dhobi.perfectdhobi.data.model.notificationmodel.NotificationResponseModel
import com.dhobi.perfectdhobi.data.model.otpmodel.OtpvalidateRequestModel
import com.dhobi.perfectdhobi.data.model.otpmodel.otpResponseModel.OtpResponseModel
import com.dhobi.perfectdhobi.data.model.place_temp_order_model.PlaceOrderRequestModel
import com.dhobi.perfectdhobi.data.model.place_temp_order_model.PlaceOrderResponseModel
import okhttp3.MultipartBody
import retrofit2.http.*


interface ApiInterface {


    @POST("sign-in")
    suspend fun login(
        @Header("devicetype") devicetype: String,
        @Header("key") key: String,
        @Header("source") source: String,
        @Body requestBody: LoginRequestModel
    ): LoginResponseModel


    @POST("validate-otp")
    suspend fun validateotp(
        @Header("devicetype") devicetype: String,
        @Header("key") key: String,
        @Header("source") source: String,
        @Body requestBody: OtpvalidateRequestModel
    ): OtpResponseModel


    @POST("sign-out")
    suspend fun logout(
        @Header("devicetype") devicetype: String,
        @Header("key") key: String,
        @Header("source") source: String
    ): LogoutResponseModel


    @POST("expire-otp")
    suspend fun expireotp(
        @Header("devicetype") devicetype: String,
        @Header("key") key: String,
        @Header("source") source: String,
        @Body requestBody: ExpriedOtpRequestModel
    ): ExpriedOtpResponseModel


    @POST("resend-otp")
    suspend fun resendotp(
        @Header("devicetype") devicetype: String,
        @Header("key") key: String,
        @Header("source") source: String,
        @Body requestBody: ExpriedOtpRequestModel
    ): LoginResponseModel


    @POST("customer/save-address")
    suspend fun saveaddress(
        @Header("devicetype") devicetype: String,
        @Header("key") key: String,
        @Header("source") source: String,
        @Body requestBody: AddAddressRequestModel
    ): AddAddressResponseModel


    @GET("customer/address-list")
    suspend fun addresslist(
        @Header("devicetype") devicetype: String,
        @Header("key") key: String,
        @Header("source") source: String,
    ): AddressResponseModel



    @POST("customer/address-details")
    suspend fun addressdetails(
        @Header("devicetype") devicetype: String,
        @Header("key") key: String,
        @Header("source") source: String,
        @Body requestBody: EditAddressRequestModel
    ): EditAddressResponseModel


    @POST("customer/update-address")
    suspend fun updateaddress(
        @Header("devicetype") devicetype: String,
        @Header("key") key: String,
        @Header("source") source: String,
        @Body requestBody: UpdateAddressRequestModel
    ): UpdateAddressResponseModel


    @POST("customer/delete-address")
    suspend fun deleteaddress(
        @Header("devicetype") devicetype: String,
        @Header("key") key: String,
        @Header("source") source: String,
        @Body requestBody: DeleteAddressRequestModel
    ): UpdateAddressResponseModel


    @POST("customer/set-primary-address")
    suspend fun primaryaddress(
        @Header("devicetype") devicetype: String,
        @Header("key") key: String,
        @Header("source") source: String,
        @Body requestBody: PrimaryAddressRequestModel
    ): UpdateAddressResponseModel



    @GET("rate-chart")
    suspend fun ratechartlist(
        @Header("devicetype") devicetype: String,
        @Header("key") key: String,
        @Header("source") source: String,
    ): RatechartResponseModel


    @GET("customer/help-and-support")
    suspend fun helpandsupportlist(
        @Header("devicetype") devicetype: String,
        @Header("key") key: String,
        @Header("source") source: String,
    ): HelpandSupportResponseModel


    @GET("banners")
    suspend fun BannerHomeTop(
        @Query("screenName") screenName: String,
        @Query("type") type: String,
        @Header("devicetype") devicetype: String,
        @Header("key") key: String,
        @Header("source") source: String,
    ): BannerResponseModel



    @GET("customer/notifications")
    suspend fun notificationlist(
        @Header("devicetype") devicetype: String,
        @Header("key") key: String,
        @Header("source") source: String,
    ): NotificationResponseModel



    @POST("customer/slot-book")
    suspend fun slotbooking(
        @Header("devicetype") devicetype: String,
        @Header("key") key: String,
        @Header("source") source: String,
        @Body requestBody: SlotBookingRequestModel
    ): SlotBookingResponseModel



    @GET("customer/booked-slots")
    suspend fun bookedslots(
        @Header("devicetype") devicetype: String,
        @Header("key") key: String,
        @Header("source") source: String,
    ): BookedSlotModelResponse



    @GET("user-profile")
    suspend fun userprofile(
        @Header("devicetype") devicetype: String,
        @Header("key") key: String,
        @Header("source") source: String,
    ): ProfileGetResponseModel




    @POST("update-user-profile")
    suspend fun updateprofile(
        @Header("devicetype") devicetype: String,
        @Header("key") key: String,
        @Header("source") source: String,
        @Body requestBody: ProfileUpdateRequestModel
    ): ProfileUpdateResponseModel



    @Multipart
    @POST("update-profile-image")
    suspend fun profilepicupdate(
        @Header("devicetype") devicetype: String,
        @Header("key") key: String,
        @Header("source") source: String,
        @Part file: MultipartBody.Part,
    ): ProfilePicResponseModel





    @GET("customer/faq")
    suspend fun faq(
        @Header("devicetype") devicetype: String,
        @Header("key") key: String,
        @Header("source") source: String,
    ): FAQResponseModel




    @GET("menu-and-addons")
    suspend fun menuandaddon(
        @Header("devicetype") devicetype: String,
        @Header("key") key: String,
        @Header("source") source: String,
    ): MenuandAddonResponseModel




    @POST("customer/create-temp-order")
    suspend fun createTempOrder(
        @Header("devicetype") devicetype: String,
        @Header("key") key: String,
        @Header("source") source: String,
        @Body menuAddOnSendRequestModel: MenuAddOnSendRequestModel
    ): MenuTempItemCreateResponseModel



    @POST("customer/temp-order-summery")
    suspend fun tempOrderSummery(
        @Header("devicetype") devicetype: String,
        @Header("key") key: String,
        @Header("source") source: String,
        @Body tempOrderSummeryRequestModel: TempOrderSummeryRequestModel
    ): TempOrderSummeryResponseModel


    @POST("customer/place-order")
    suspend fun placeTempOrder(
        @Header("devicetype") devicetype: String,
        @Header("key") key: String,
        @Header("source") source: String,
        @Body placeOrderRequestModel: PlaceOrderRequestModel
    ): PlaceOrderResponseModel



    @POST("customer/recharge-wallet")
    suspend fun rechargeWallet(
        @Header("devicetype") devicetype: String,
        @Header("key") key: String,
        @Header("source") source: String,
        @Body rechargeWalletRequestModel: RechargeWalletRequestModel
    ): RechargeWalletResponseModel


    @POST("customer/recharge-faild")
    suspend fun rechargeWalletFailed(
        @Header("devicetype") devicetype: String,
        @Header("key") key: String,
        @Header("source") source: String,
        @Body rechargeWalletFailedRequestModel: RechargeWalletFailedRequestModel
    ): CommonResponseModel


    @POST("customer/recharge-verify")
    suspend fun rechargeWalletVerify(
        @Header("devicetype") devicetype: String,
        @Header("key") key: String,
        @Header("source") source: String,
        @Body rechargeWalletVerifiedRequestModel: RechargeWalletVerifiedRequestModel
    ): CommonResponseModel



    @GET("customer/wallet-balance")
    suspend fun walletBalance(
        @Header("devicetype") devicetype: String,
        @Header("key") key: String,
        @Header("source") source: String
    ): WalletResponseModel




//    @POST("user/login")
//    suspend fun login(
//        @Body requestBody: LoginRequestModel
//    ): LoginResponseModel
//
//
//
//    @POST("user/urc-list")
//    suspend fun urclist(
//        @Body requestBody: UrcRequestModel
//    ): UrcModel
//
//
//    @POST("user/urc-category")
//    suspend fun categoryall(
//        @Body requestBody: CategoryRequestModel
//    ): CategoryResponseModel
//
//
//    @POST("user/urc-dashboard")
//    suspend fun dashboard(
//        @Body requestBody: DashboardRequestModel
//    ): DashboardResponseModel
//
//    @POST("user/urc-product-list")
//    suspend fun productList(
//        @Body requestBody: ProductListRequestModel,
//        @Query("page") page: String
//    ): ProductListResponseModel
//
//    @GET("user/urc-product-details/{id}")
//    suspend fun getProductDetails(
//        @Path("id") id: String
//    ): ProductDetailsResponseModel
//
//    @GET("user/logout")
//    suspend fun logout(
//    ): LogoutResponseModel
//
//    @POST("user/forgot-password")
//    suspend fun forgotpassword(
//        @Body requestBody: ForgetPassRequestModel
//    ): ForgetPasswordResponseModel
//
//
//    @POST("user/cart-add")
//    suspend fun cartadd(
//        @Body requestBody: CartRequestModel
//    ): CartResponseModel
//
//    @GET("user/cart-delete/{id}")
//    suspend fun cartDelete(
//        @Path("id") id: String
//    ): CartResponseModel
//
//    @GET("user/cart-list")
//    suspend fun cartList(): CartListResponseModel
//
//    @GET("user/address-list")
//    suspend fun addressList(): AddressListResponseModel
//
//    @GET("user/address-delete/{id}")
//    suspend fun addressDelete(
//        @Path("id") id: String
//    ): AddAddressResponseModel
//
//    @GET("user/address-primary/{id}")
//    suspend fun addressPrimary(
//        @Path("id") id: String
//    ): AddAddressResponseModel
//
//    @POST("user/address-add")
//    suspend fun addAddress(
//        @Body requestBody: AddAddressRequestModel
//    ): AddAddressResponseModel
//
//    @POST("user/address-edit")
//    suspend fun editAddress(
//        @Body requestBody: AddAddressRequestModel
//    ): AddAddressResponseModel
//
//
//    @POST("user/wishlist-add")
//    suspend fun addtoWishlist(
//        @Body requestBody: AddWishlistRequestModel
//    ): AddtowishlistResponseModel
//
//
//    @GET("user/wishlist-list")
//    suspend fun wishlist(): WishListResponseModel
//
//
//    @GET("user/wishlist-delete/{id}")
//    suspend fun wishlistDelete(
//        @Path("id") id: String
//    ): AddtowishlistResponseModel
//
//
//    @GET("user/notification-list")
//    suspend fun notificationlist(): NotificationModelResponse
//
//
//    @POST("user/paymentcard-add")
//    suspend fun addpaymentcard(
//        @Body requestBody: AddcardRequestModel
//    ): AddcardResponseModel
//
//    @GET("user/paymentcard-list")
//    suspend fun paymentcardlist(): SaveCardListResponseModel
//
//
//    @GET("user/paymentcard-primary/{id}")
//    suspend fun paymentcardPrimary(
//        @Path("id") id: String
//    ): AddPaymentPrimaryCardResponse
//
//
//    @GET("user/paymentcard-delete/{id}")
//    suspend fun paymentcardDelete(
//        @Path("id") id: String
//    ): AddPaymentPrimaryCardResponse
//
//
//    @GET("user/order-summary")
//    suspend fun orderSummeryDetails(): OrderSummeryResponseModel
//
//    @GET("user/order-list")
//    suspend fun myOrderList(): MyOrderListResponseModel
//
//    @POST("user/add-order")
//    suspend fun addOrderDetails(
//        @Body requestBody: AddOrderRequestModel
//    ): AddOrderResponseModel
//
//    @GET("user/order-details/{id}")
//    suspend fun orderDetails(
//        @Path("id") id: String
//    ): OrderDetailsResponseModel
//
//    @GET("user/content-lists/{id}")
//    suspend fun getSupportAndFAQ(
//        @Path("id") id: String
//    ): SupportFAQResponseModel
//
//
//    @POST("user/order-payment")
//    suspend fun orderpayment(
//        @Body requestBody: OrderPaymentRequestModel
//    ): OrderPaymentResponseModel
//
//    @Multipart
//    @POST("user/profile-edit")
//    suspend fun profileupdate(
//        @Part("name") name: RequestBody,
//        @Part("last_name") last_name: RequestBody,
//        @Part("phone") phone: RequestBody,
//        @Part("gender") gender: RequestBody,
//        @Part("birthday") birthday: RequestBody,
//        @Part("password") password: RequestBody,
//        @Part("old_password") old_password: RequestBody,
//        @Part file: MultipartBody.Part,
//    ): ProfileUpdateResponseModel
//
//
//
//    @GET("user/profile")
//    suspend fun profileget(): ProfileDetailsResponse
//
//    @GET("user/attribute-list")
//    suspend fun filterResponse(): FilterResponseModel

//    @Multipart
//    @POST(NetworkConstants.ENDPOINT_USER_REGISTER)
//    fun postRegister1(@Body registerRequest: RegisterRequest): Single<CommonResponseModel>
//
//    @Multipart
//    @POST(NetworkConstants.ENDPOINT_USER_REGISTER)
//    fun postRegister(
//        @Part("name") title: RequestBody,
//        @Part("email") email: RequestBody,
//        @Part("password") password: RequestBody,
//        @Part("society_id") societyId: RequestBody,
//        @Part aadharDocument: MultipartBody.Part,
//        @Part idFile: MultipartBody.Part,
//        @Part("phone_number") phone: RequestBody,
//        @Part("language") language: RequestBody,
//    ): Call<RegisterResponse?>
//
//    @Multipart
//    @POST(NetworkConstants.ENDPOINT_PROFILE_UPDATE)
//    fun postProfileUpdate(
//        @Part("name") title: RequestBody,
//        @Part("email") email: RequestBody,
//        @Part("society_id") societyId: RequestBody,
//        @Part aadharDocument: MultipartBody.Part,
//        @Part idFile: MultipartBody.Part,
//        @Part proileFile: MultipartBody.Part,
//        @Part("phone_number") phone: RequestBody,
//        @Part("user_id") userId: RequestBody,
//    ): Call<RegisterResponse?>
//
//    @GET(NetworkConstants.ENDPOINT_USER_MOBILE_OTP)
//    fun otp(@Query("phone_no") phone_no: Long): Single<CommonResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_USER_FORGET_PASS)
//    fun forgotPassword(@Body forgetPassRequestModel: ForgetPassRequestModel): Single<ForgetPasswordResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_USER_LOGIN)
//    fun login(@Body loginRequestModel: LoginRequestModel): Single<LoginResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_USER_CHANGE_LANGUAGE)
//    fun changeLanguage(@Body changeLanguageRequestModel: ChangeLanguageRequestModel): Single<CommonResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_USER_PROFILE_DETAILS)
//    fun profileDetails(@Body getProfileRequestModel: GetProfileRequestModel): Single<ProfileResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_HUB_LIST)
//    fun hubListDetails(@Body getProfileRequestModel: GetProfileRequestModel): Single<HubListResponse>
//
//    @POST(NetworkConstants.ENDPOINT_DELETE_CART_ITEM)
//    fun deleteCartItem(@Body deleteCartItemRequestModel: DeleteCartItemRequestModel): Single<CommonResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_GET_CART)
//    fun getCartDetails(@Body getProfileRequestModel: GetProfileRequestModel): Single<GetCartResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_CART_CHECKOUT)
//    fun getCartCheckout(@Body checkoutRequestModel: CheckoutRequestModel): Single<CheckoutResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_CATEGORY_LIST)
//    fun categoryListDetails(@Body getProfileRequestModel: GetProfileRequestModel): Single<CategoryListResponse>
//    @POST(NetworkConstants.ENDPOINT_PAYMENT_STATUS)
//    fun postPaymentStatus(@Body paymentStatusRequestModel: PayNowRequestModel): Single<PayNowResponseModel>
//    @POST(NetworkConstants.ENDPOINT_ITEM_PARTICULAR_LIST)
//    fun itemParticularListDetails(@Body getItemParticulateRequestModel: GetItemParticulateRequestModel): Single<ItemParticualarResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_PAST_ORDER)
//    fun pastOrderListDetails(@Body profileRequestModel: GetProfileRequestModel): Single<MyOrderResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_UPCOMING_ORDER)
//    fun upcomingtOrderListDetails(@Body profileRequestModel: GetProfileRequestModel): Single<MyOrderResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_NOTIFICATION)
//    fun notificationListDetails(@Body profileRequestModel: GetProfileRequestModel): Single<NotificationResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_CHANGEPASSWORD)
//    fun changePassword(@Body changePasswordRequestModel: ChangePasswordRequestModel): Single<CommonResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_SUPPLY_MILL_LIST)
//    fun millListDetails(@Body millRequestModel: SupplyMillRequestModel): Single<SupplyMillResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_ADD_TO_CART)
//    fun addToCart(@Body addToCartRequesModel: AddToCartRequesModel): Single<CommonResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_PAY_NOW)
//    fun postPayNow(@Body payNowRequestModel: PayNowRequestModel): Single<PayNowResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_USER_CREATE_PASSWORD)
//    fun createPassword(@Body loginRequestModel: LoginRequestModel): Single<CommonResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_USER_RESEND_OTP)
//    fun resendOTP(@Body forgetPassRequestModel: ForgetPassRequestModel): Single<ForgetPasswordResponseModel>
//
//    @GET(NetworkConstants.ENDPOINT_SOCIETY_LIST)
//    fun societyList(): Single<SocietyListResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_LOGOUT)
//    fun logout(@Body getProfileRequestModel: GetProfileRequestModel): Single<CommonResponseModel>

}