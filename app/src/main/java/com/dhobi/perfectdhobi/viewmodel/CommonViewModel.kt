package com.dhobi.perfectdhobi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dhobi.perfectdhobi.data.model.AddAddressModel.AddAddressRequestModel
import com.dhobi.perfectdhobi.data.model.DeleteAddressModel.DeleteAddressRequestModel
import com.dhobi.perfectdhobi.data.model.EditAddressModel.EditAddressRequestModel.EditAddressRequestModel
import com.dhobi.perfectdhobi.data.model.PrimaryAddressSetModel.PrimaryAddressRequestModel
import com.dhobi.perfectdhobi.data.model.ProfileUpdateModel.ProfileUpdateRequestModel
import com.dhobi.perfectdhobi.data.model.SlotBookingModel.SlotBookingRequestModel
import com.dhobi.perfectdhobi.data.model.UpdateAddressModel.UpdateAddressRequestModel
import com.dhobi.perfectdhobi.data.model.add_to_wallet.RechargeWalletFailedRequestModel
import com.dhobi.perfectdhobi.data.model.add_to_wallet.RechargeWalletRequestModel
import com.dhobi.perfectdhobi.data.model.add_to_wallet.RechargeWalletVerifiedRequestModel
import com.dhobi.perfectdhobi.data.model.expriedotp.ExpriedOtpRequestModel
import com.dhobi.perfectdhobi.data.model.loginmodel.LoginRequestModel
import com.dhobi.perfectdhobi.data.model.menu_and_addon_model.MenuAddOnSendRequestModel
import com.dhobi.perfectdhobi.data.model.menu_and_addon_model.TempOrderSummeryRequestModel
import com.dhobi.perfectdhobi.data.model.otpmodel.OtpvalidateRequestModel
import com.dhobi.perfectdhobi.data.model.place_temp_order_model.PlaceOrderRequestModel
import com.dhobi.perfectdhobi.data.repository.MainRepository
import com.grocery.sainik_grocery.data.Resource
import kotlinx.coroutines.Dispatchers
import okhttp3.MultipartBody


class CommonViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun login(devicetype: String, key: String, source: String, requestBody: LoginRequestModel) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))

            try {
                emit(
                    Resource.success(
                        data = mainRepository.login(
                            devicetype,
                            key,
                            source,
                            requestBody
                        )
                    )
                )
            } catch (e: Exception) {
                emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
            }
        }


    fun validateotp(
        devicetype: String,
        key: String,
        source: String,
        requestBody: OtpvalidateRequestModel
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(
                Resource.success(
                    data = mainRepository.validateotp(
                        devicetype,
                        key,
                        source,
                        requestBody
                    )
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }


    fun logout(devicetype: String, key: String, source: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(Resource.success(data = mainRepository.logout(devicetype, key, source)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }


    fun expireotp(
        devicetype: String,
        key: String,
        source: String,
        requestBody: ExpriedOtpRequestModel
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(
                Resource.success(
                    data = mainRepository.expireotp(
                        devicetype,
                        key,
                        source,
                        requestBody
                    )
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }


    fun resendotp(
        devicetype: String,
        key: String,
        source: String,
        requestBody: ExpriedOtpRequestModel
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(
                Resource.success(
                    data = mainRepository.resendotp(
                        devicetype,
                        key,
                        source,
                        requestBody
                    )
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }


    fun saveaddress(
        devicetype: String,
        key: String,
        source: String,
        requestBody: AddAddressRequestModel
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(
                Resource.success(
                    data = mainRepository.saveaddress(
                        devicetype,
                        key,
                        source,
                        requestBody
                    )
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }


    fun addresslist(devicetype: String, key: String, source: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(Resource.success(data = mainRepository.addresslist(devicetype, key, source)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }


    fun addressdetails(
        devicetype: String,
        key: String,
        source: String,
        requestBody: EditAddressRequestModel
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(
                Resource.success(
                    data = mainRepository.addressdetails(
                        devicetype,
                        key,
                        source,
                        requestBody
                    )
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }


    fun updateaddress(
        devicetype: String,
        key: String,
        source: String,
        requestBody: UpdateAddressRequestModel
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(
                Resource.success(
                    data = mainRepository.updateaddress(
                        devicetype,
                        key,
                        source,
                        requestBody
                    )
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }


    fun deleteaddress(
        devicetype: String,
        key: String,
        source: String,
        requestBody: DeleteAddressRequestModel
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(
                Resource.success(
                    data = mainRepository.deleteaddress(
                        devicetype,
                        key,
                        source,
                        requestBody
                    )
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }


    fun primaryaddress(
        devicetype: String,
        key: String,
        source: String,
        requestBody: PrimaryAddressRequestModel
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(
                Resource.success(
                    data = mainRepository.primaryaddress(
                        devicetype,
                        key,
                        source,
                        requestBody
                    )
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }


    fun ratechartlist(devicetype: String, key: String, source: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(Resource.success(data = mainRepository.ratechartlist(devicetype, key, source)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }


    fun helpandsupportlist(devicetype: String, key: String, source: String) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))

            try {
                emit(
                    Resource.success(
                        data = mainRepository.helpandsupportlist(
                            devicetype,
                            key,
                            source
                        )
                    )
                )
            } catch (e: Exception) {
                emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
            }
        }


    fun BannerHomeTop(
        screenName: String,
        type: String,
        devicetype: String,
        key: String,
        source: String
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(
                Resource.success(
                    data = mainRepository.BannerHomeTop(
                        screenName,
                        type,
                        devicetype,
                        key,
                        source
                    )
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }


    fun notificationlist(devicetype: String, key: String, source: String) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))

            try {
                emit(
                    Resource.success(
                        data = mainRepository.notificationlist(
                            devicetype,
                            key,
                            source
                        )
                    )
                )
            } catch (e: Exception) {
                emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
            }
        }


    fun slotbooking(
        devicetype: String,
        key: String,
        source: String,
        requestBody: SlotBookingRequestModel
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(
                Resource.success(
                    data = mainRepository.slotbooking(
                        devicetype,
                        key,
                        source,
                        requestBody
                    )
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }


    fun bookedslots(devicetype: String, key: String, source: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(Resource.success(data = mainRepository.bookedslots(devicetype, key, source)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }


    fun userprofile(devicetype: String, key: String, source: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(Resource.success(data = mainRepository.userprofile(devicetype, key, source)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }


    fun updateprofile(
        devicetype: String,
        key: String,
        source: String,
        requestBody: ProfileUpdateRequestModel
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(
                Resource.success(
                    data = mainRepository.updateprofile(
                        devicetype,
                        key,
                        source,
                        requestBody
                    )
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }


    fun profilepicupdate(
        devicetype: String,
        key: String,
        source: String,
        part: MultipartBody.Part
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.profilepicupdate(
                        devicetype,
                        key,
                        source,
                        part
                    )
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }


    fun faq(devicetype: String, key: String, source: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.faq(devicetype, key, source)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }


    fun menuandaddon(devicetype: String, key: String, source: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.menuandaddon(devicetype, key, source)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }


    fun createTempOrder(
        devicetype: String,
        key: String,
        source: String,
        menuAddOnSendRequestModel: MenuAddOnSendRequestModel
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.createTempOrder(
                        devicetype,
                        key,
                        source,
                        menuAddOnSendRequestModel
                    )
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }

    fun tempOrderSummery(
        devicetype: String,
        key: String,
        source: String,
        tempOrderSummeryRequestModel: TempOrderSummeryRequestModel
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.tempOrderSummery(
                        devicetype,
                        key,
                        source,
                        tempOrderSummeryRequestModel
                    )
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }


    fun placeTempOrder(
        devicetype: String,
        key: String,
        source: String,
        placeOrderRequestModel: PlaceOrderRequestModel
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.placeTempOrder(
                        devicetype,
                        key,
                        source,
                        placeOrderRequestModel
                    )
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }

    fun rechargeWallet(
        devicetype: String,
        key: String,
        source: String,
        rechargeWalletRequestModel: RechargeWalletRequestModel
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.rechargeWallet(
                        devicetype,
                        key,
                        source,
                        rechargeWalletRequestModel
                    )
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }

    fun rechargeWalletFailed(
        devicetype: String,
        key: String,
        source: String,
        rechargeWalletFailedRequestModel: RechargeWalletFailedRequestModel
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.rechargeWalletFailed(
                        devicetype,
                        key,
                        source,
                        rechargeWalletFailedRequestModel
                    )
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }


    fun rechargeWalletVerify(
        devicetype: String,
        key: String,
        source: String,
        rechargeWalletVerifiedRequestModel: RechargeWalletVerifiedRequestModel
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.rechargeWalletVerify(
                        devicetype,
                        key,
                        source,
                        rechargeWalletVerifiedRequestModel
                    )
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }

    fun walletBalance(
        devicetype: String,
        key: String,
        source: String
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.walletBalance(
                        devicetype,
                        key,
                        source
                    )
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }

//    var cartListItem: MutableLiveData<Int> = MutableLiveData()
//    fun login(requestBody: LoginRequestModel) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.login(requestBody)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//
//
//    fun urclist(requestBody: UrcRequestModel) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.urclist(requestBody)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//
//
//    fun categoryall(requestBody: CategoryRequestModel) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.categoryall(requestBody)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//
//    fun productList(requestBody: ProductListRequestModel,page:String) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.getAllProduct(requestBody,page)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//    fun productDetails(id:String) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.getProductDetails(id)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//
//    fun cartadd(requestBody: CartRequestModel) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.cartadd(requestBody)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//    fun cartDelete(id:String) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.cartDelete(id)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//    fun cartList() = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.cartList()
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//    fun addressList() = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.addressList()
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//    fun addAddress(requestBody: AddAddressRequestModel) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.addAddress(requestBody)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//    fun editAddress(requestBody: AddAddressRequestModel) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.editAddress(requestBody)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//    fun deleteAddress(id:String) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.deleteAddress(id)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//    fun primaryAddress(id:String) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.primaryAddress(id)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//    fun dashboard(requestBody: DashboardRequestModel) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.dashboard(requestBody)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//    fun logout() = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.logout()
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//    fun forgotpassword(requestBody: ForgetPassRequestModel) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.forgotpassword(requestBody)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//
//
//    fun addtoWishlist(requestBody: AddWishlistRequestModel) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.addtoWishlist(requestBody)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//
//
//    fun wishlist() = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.wishlist()
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//
//
//    fun wishlistDelete(id:String) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.wishlistDelete(id)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//
//
//    fun notificationlist() = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.notificationlist()
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//
//
//
//    fun addpaymentcard(requestBody: AddcardRequestModel) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.addpaymentcard(requestBody)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//    fun paymentcardlist() = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.paymentcardlist()
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//
//
//
//    fun paymentcardPrimary(id:String) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.paymentcardPrimary(id)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//
//
//
//    fun paymentcardDelete(id:String) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.paymentcardDelete(id)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//    fun addOrderDetails(requestBody: AddOrderRequestModel) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.addOrderDetails(requestBody)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//    fun orderSummeryDetails() = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.orderSummeryDetails()
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//
//    fun myOrderList() = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.myOrderList()
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//    fun orderDetails(id:String) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.orderDetails(id)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//
//    fun getSupportAndFAQ(id:String) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.getSupportAndFAQ(id)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//    fun orderpayment(requestBody: OrderPaymentRequestModel) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.orderpayment(requestBody)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//    fun profileupdate(
//        name: RequestBody,
//        last_name: RequestBody,
//        phone: RequestBody,
//        gender: RequestBody,
//        birthday: RequestBody,
//        password: RequestBody,
//        old_password: RequestBody,
//        part: MultipartBody.Part) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//
//        try {
//            emit(Resource.success(data = mainRepository.profileupdate(
//                name,
//                last_name,
//                phone,
//                gender,
//                birthday,
//                password,
//                old_password,
//                part)))
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//
//
//
//    fun profileget() = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.profileget()
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//    fun filterResponse() = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.filterResponse()
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
}