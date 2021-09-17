package com.example.spaapp.admin;

public class BookingsModel {
    private String id;
    private String client_name;
    private String client_phone;
    private String client_service;
    private String client_time;
    private String client_date;

    //constructor
    public BookingsModel(){

    }


    public  BookingsModel(String id,String client_name,String client_phone,String client_service,String client_time,String client_date){
           this.id = id;
           this.client_name = client_name;
           this.client_phone = client_phone;
           this.client_service = client_service;
           this.client_time = client_time;
           this.client_date = client_date;
    }


    public String getId() {
        return id;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getClient_phone() {
        return client_phone;
    }

    public void setClient_phone(String client_phone) {
        this.client_phone = client_phone;
    }

    public String getClient_service() {
        return client_service;
    }

    public void setClient_service(String client_service) {
        this.client_service = client_service;
    }

    public String getClient_time() {
        return client_time;
    }

    public void setClient_time(String client_time) {
        this.client_time = client_time;
    }

    public String getClient_date() {
        return client_date;
    }

    public void setClient_date(String client_date) {
        this.client_date = client_date;
    }
}
