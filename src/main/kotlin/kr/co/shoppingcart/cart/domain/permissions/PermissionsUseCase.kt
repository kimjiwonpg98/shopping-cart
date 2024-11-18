package kr.co.shoppingcart.cart.domain.permissions

import kr.co.shoppingcart.cart.domain.permissions.services.OwnerPermissionService
import kr.co.shoppingcart.cart.domain.permissions.services.ReaderPermissionService
import kr.co.shoppingcart.cart.domain.permissions.services.WriterPermissionService
import org.springframework.stereotype.Service

@Service
class PermissionsUseCase(
    private val ownerPermissionService: OwnerPermissionService,
    private val writerPermissionService: WriterPermissionService,
    private val readerPermissionService: ReaderPermissionService,
)
