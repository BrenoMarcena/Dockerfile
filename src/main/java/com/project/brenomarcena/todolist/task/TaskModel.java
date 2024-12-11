package com.project.brenomarcena.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tb_tasks")
public class TaskModel {
    

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String description;

    @Column(length = 50)
    private String title;
    private String priority;
    private LocalDateTime startAT;
    private LocalDateTime endAT;
    
    private UUID idUser;
    
    @CreationTimestamp
    private LocalDateTime createdAT;

    public void setTitle(String title) throws Exception {
        if(title.length() > 50){
            throw new Exception("O campo título deve ter menos de 50 caracteres");
        }
        this.title = title;
    }
    
}
