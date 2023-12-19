package com.example.model.response;

import lombok.Data;

import java.util.List;

@Data
public class JournalResponsePage {

    private List<JournalResponse> journalResponses;
    private long totalElement;
    private int totalPage;
}
