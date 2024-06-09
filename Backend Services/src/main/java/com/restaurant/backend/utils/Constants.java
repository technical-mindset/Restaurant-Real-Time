package com.restaurant.backend.utils;


/**
 @author Asad Zaidi
  */

public class Constants {

    // -----------  ---------  Related To Data Per Page  --------- ----------
    public static final String PAGE_NUMBER = "0";
    public static final String PAGE_SIZE = "10";
    public static final String SORT_BY = "id";
    public static final String DATE = "1";

    // -----------  ---------  Headers Section For Excel Report --------- ----------
    public static final String[] HEADERS = {
      "Id", "Name", "Total Order", "Price", "Total Revenue", "Total Sale", "Sale Date"
    };
    public static final String DEAL_REPORT_SHEET_NAME = "Deal_Report";
    public static final String ITEM_REPORT_SHEET_NAME = "Item_Report";

    // -----------  ---------  Response Messages --------- ----------
    public static final String MESSAGE_FETCHED = "Fetched Successfully!";
    public static final String MESSAGE_UPDATED = "Updated Successfully!";
    public static final String MESSAGE_ADDED = "Added Successfully!";
    public static final String MESSAGE_DELETED = "Deleted Successfully!";

    // -----------  ---------  Request URI --------- ----------
    public static final String ADD_UPDATE_URI = "/addUpdate";
    public static final String ITEM_CATEGORY_URI = "/itemcategory";
    public static final String ITEM_URI = "/item";
    public static final String Deal_URI = "/deal";
    public static final String Order_URI = "/order";
    public static final String Table_URI = "/table";
    public static final String User_URI = "/user";

    // ----------  ---------- Constants For DTO -------- -----------
    public static final String NO_DATA_FOUND = "No Data Found :(";
    public static final String EXIST_MESSAGE = "This Value is Already Exist";
    public static final String EMPTY_MESSAGE = "must not be Empty";
    public static final String MIN_VALUE = "must be greater than or equal to 3";
    public static final String MAX_VALUE = "must be short !!";
    public static final String LESS_VALUE = "must be greater than or equal to 1 !!";



}
