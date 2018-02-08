package com.example.wmramazan.getirhackathontask3;

/**
 * Created by wmramazan on 8.02.2018.
 */

public class RecordItem {

    private String createdAt;
    private int totalCount;

    public RecordItem(String createdAt, int totalCount) {

        this.createdAt = createdAt;
        this.totalCount = totalCount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public int getTotalCount() {
        return totalCount;
    }
}
