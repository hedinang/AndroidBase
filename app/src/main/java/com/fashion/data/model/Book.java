package com.fashion.data.model;

import java.util.List;

public class Book {
    private String id;
    private String bookId;
    public String title;
    public String subtitle;
    public String quote;
    public String preview;
    public String author;
    public String publishDay;
    public String year;
    public String bgColor;
    public Boolean isBookmark;
    public String chapterTotal;
    public int duration;
    public Image image;
    public Audio audio;
    public List<Category> bookCategories;

    public String getId() {
        if (id == null)
            return bookId;
        else
            return id;
    }

    public void setId(String id) {
        this.bookId = id;
        this.id = id;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.id = bookId;
        this.bookId = bookId;
    }

    public String getCategories() {
        if (bookCategories != null && !bookCategories.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            for (Category category : bookCategories) {
                builder.append(category.nameVi);
                builder.append(", ");
            }
            return builder.toString();
        }
        else
            return "";
    }

    public String getAuthorAndYear(){
        return author.replace("Tác giả: ", "") + ", " + year;
    }
}
