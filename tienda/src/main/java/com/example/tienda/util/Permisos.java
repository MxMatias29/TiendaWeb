package com.example.tienda.util;

public enum Permisos {

    // USUARIO ✅
    READ_ALL_TIENDA, 
    READ_ALL_USER_DTO_ADMIN,
    READ_ALL_USER_ACTIVE_ADMIN,
    READ_ALL_USER_INACTIVE_ADMIN,
    MY_PROFILE_SHOW,
    SEARCH_USUARIO,
    CREATE_ACCOUNT,
    DISABLE_ACCOUNT,
    EDIT_PROFILE,
    DELETE_ACCOUNT,
    CART_ADD_ITEM,
    BUY_CART,
    BUY_HISTORY,
    BUY_LIST_ADMIN,
    CLEAR_CART,
    DELETE_ITEM,
    EDIT_STOCK,
    MY_LIST_CART,
    EDIT_PASSWORD,
    


    // PRODUCTO ✅
    VIEW_SALES_BETWEEN_DATES,
    VIEW_SALES_GREATER_THAN,
    VIEW_SALES_AFTER_DATE,
    VIEW_ONLINE_SALES,
    VIEW_SALES_TOTAL_BY_DATE,
    READ_ALL_PRODUCTS_ADMIN,
    READ_ALL_ACTIVES_ADMIN,
    READ_ALL_INACTIVES_ADMIN,
    SEARCH_PRODUCT_ID_ADMIN,
    SEARCH_PRODUCT_NAME_ADMIN, 
    SEARCH_PRODUCT_MARCA_USER, //
    SEARCH_PRODUCT_CATEGORY_USER, //
    SEARCH_PRODUCT_BARRA_USER, //
    SAVE_ONE_PRODUCT,
    EDIT_ONE_PRODUCT,
    DELETE_PRODUCT,
    CHANGE_ACTIVIDAD_PRODUCT,
    REPORT_PDF_PRODUCT,
    REPORT_EXCEL_PRODUCT,

    // CATEGORIA ✅
    READ_ALL_CATEGORY,
    READ_ALL_ACTIVE_CATEGORY,//
    READ_ALL_INACTIVE_CATEGORY,
    SEARCH_CATEGORY_ID,
    SAVE_CATEGORY,
    EDIT_CATEGORY,
    CHANGE_ACTIVIDAD_CATEGORY,
    DELETE_CATEGORY,

    // ESTADO PRODUCTO ✅
    READ_ALL_ESTADO_PRODUCTO,
    READ__ALL_ACTIVE_ESTADO_PRODUCTO, //
    READ_ALL_INACTIVE_ESTADO_PRODUCTO,
    SEARCH_ESTADO_PRODUCTO_ID,
    SAVE_ESTADO_PRODUCTO,
    EDIT_ESTADO_PRODUCTO,
    CHANGE_ACTIVIDAD_ESTADO_PRODUCTO,
    DELETE_ESTADO_PRODUCTO,

    // MARCA ✅
    READ_ALL_MARCA,
    READ_ALL_ACTIVE_MARCA,//
    READ_ALL_INACTIVE_MARCA,
    SEARCH_MARCA_ID,
    SAVE_MARCA,
    EDIT_MARCA,
    CHANGE_ACTIVIDAD_MARCA,
    DELETE_MARCA,

    // PROVEEDOR ✅
    READ_ALL_PROVEEDOR,
    READ_ALL_ACTIVE_PROVEEDOR,
    READ_ALL_INACTIVE_PROVEEDOR,
    SEARCH_ID_PROVEEDOR,
    SEARCH_NAME_PROVEEDOR,
    SAVE_PROVEEDOR,
    EDIT_PROVEEDOR,
    CHANGE_ACTIVIDAD_PROVEEDOR,
    DELETE_PROVEEDOR,
    REPORT_PDF_PROVEEDOR,
    REPORT_EXCEL_PROVEEDOR

}
