package com.nanobookv2.data.model;

public class BookMarkDTO {
    public static class Request{
        public String bookId;
    }

    public static class Response {
        public String userId;
        public String bookId;
    }
}
