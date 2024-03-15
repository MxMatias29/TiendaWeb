package com.example.tienda.util;

import java.util.Arrays;
import java.util.List;

public enum Role {

    CUSTOMER(Arrays.asList(Permisos.READ_ALL_TIENDA,
            Permisos.SEARCH_PRODUCT_BARRA_USER,
            Permisos.SEARCH_PRODUCT_MARCA_USER,
            Permisos.SEARCH_PRODUCT_CATEGORY_USER,
            Permisos.SEARCH_PRODUCT_BARRA_USER,
            Permisos.READ_ALL_CATEGORY,
            Permisos.READ_ALL_MARCA,
            Permisos.MY_PROFILE_SHOW,
            Permisos.SEARCH_USUARIO,
            Permisos.EDIT_PROFILE,
            Permisos.DELETE_ACCOUNT,
            Permisos.CART_ADD_ITEM,
            Permisos.BUY_CART,
            Permisos.BUY_HISTORY,
            Permisos.EDIT_PASSWORD,
            Permisos.CLEAR_CART,
            Permisos.EDIT_STOCK,
            Permisos.DELETE_ITEM,
            Permisos.MY_LIST_CART)),

    ADMINISTRADOR(Arrays.asList(Permisos.READ_ALL_ACTIVES_ADMIN,
            Permisos.VIEW_SALES_BETWEEN_DATES,
            Permisos.VIEW_SALES_GREATER_THAN,
            Permisos.VIEW_SALES_AFTER_DATE,
            Permisos.VIEW_ONLINE_SALES,
            Permisos.VIEW_SALES_TOTAL_BY_DATE,
            Permisos.BUY_LIST_ADMIN,
            Permisos.MY_PROFILE_SHOW,
            Permisos.READ_ALL_USER_DTO_ADMIN,
            Permisos.READ_ALL_USER_ACTIVE_ADMIN,
            Permisos.READ_ALL_USER_INACTIVE_ADMIN,
            Permisos.DISABLE_ACCOUNT,
            Permisos.READ_ALL_PRODUCTS_ADMIN,
            Permisos.READ_ALL_ACTIVES_ADMIN,
            Permisos.READ_ALL_INACTIVES_ADMIN,
            Permisos.SEARCH_PRODUCT_ID_ADMIN,
            Permisos.SEARCH_PRODUCT_NAME_ADMIN,
            Permisos.SAVE_ONE_PRODUCT,
            Permisos.EDIT_ONE_PRODUCT,
            Permisos.DELETE_PRODUCT,
            Permisos.CHANGE_ACTIVIDAD_PRODUCT,
            Permisos.REPORT_PDF_PRODUCT,
            Permisos.REPORT_EXCEL_PRODUCT,
            Permisos.READ_ALL_CATEGORY,
            Permisos.READ_ALL_ACTIVE_CATEGORY,
            Permisos.READ_ALL_INACTIVE_CATEGORY,
            Permisos.SEARCH_CATEGORY_ID,
            Permisos.SAVE_CATEGORY,
            Permisos.EDIT_CATEGORY,
            Permisos.CHANGE_ACTIVIDAD_CATEGORY,
            Permisos.DELETE_CATEGORY,
            Permisos.READ_ALL_ESTADO_PRODUCTO,
            Permisos.READ__ALL_ACTIVE_ESTADO_PRODUCTO,
            Permisos.READ_ALL_INACTIVE_ESTADO_PRODUCTO,
            Permisos.SEARCH_ESTADO_PRODUCTO_ID,
            Permisos.SAVE_ESTADO_PRODUCTO,
            Permisos.EDIT_ESTADO_PRODUCTO,
            Permisos.CHANGE_ACTIVIDAD_ESTADO_PRODUCTO,
            Permisos.DELETE_ESTADO_PRODUCTO,
            Permisos.READ_ALL_MARCA,
            Permisos.READ_ALL_ACTIVE_MARCA,
            Permisos.READ_ALL_INACTIVE_MARCA,
            Permisos.SEARCH_MARCA_ID,
            Permisos.SAVE_MARCA,
            Permisos.EDIT_MARCA,
            Permisos.CHANGE_ACTIVIDAD_MARCA,
            Permisos.DELETE_MARCA,
            Permisos.READ_ALL_PROVEEDOR,
            Permisos.READ_ALL_ACTIVE_PROVEEDOR,
            Permisos.READ_ALL_INACTIVE_PROVEEDOR,
            Permisos.SEARCH_ID_PROVEEDOR,
            Permisos.SEARCH_NAME_PROVEEDOR,
            Permisos.SAVE_PROVEEDOR,
            Permisos.EDIT_PROVEEDOR,
            Permisos.CHANGE_ACTIVIDAD_PROVEEDOR,
            Permisos.DELETE_PROVEEDOR,
            Permisos.REPORT_PDF_PROVEEDOR,
            Permisos.REPORT_EXCEL_PROVEEDOR));

    private List<Permisos> permisos;

    private Role(List<Permisos> permisos) {
        this.permisos = permisos;
    }

    public List<Permisos> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<Permisos> permisos) {
        this.permisos = permisos;
    }

}
