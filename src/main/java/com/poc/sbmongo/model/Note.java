package com.poc.sbmongo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "notes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Note {
    @Id
    private String id;
    private String title;

    //TODO: Make Foreign Key and create separate collection for it.
    private String primaryTopic;
    private String description;
    private List<String> resources;

    //TODO: create list of tags and create separate collection of tags for tag based search.
    //private List<String> tags;

    @CreatedDate
    private Date createdDate;

    @LastModifiedDate
    private Date lastModifiedDate;

    public Note(String title, String primaryTopic, String description, List<String> resources) {
        this.title = title;
        this.primaryTopic = primaryTopic;
        this.description = description;
        this.resources = resources;
    }

//TODO: Apply security and add auditor config for user : https://medium.com/codex/spring-data-mongodb-auditing-b4a874442a6
}
