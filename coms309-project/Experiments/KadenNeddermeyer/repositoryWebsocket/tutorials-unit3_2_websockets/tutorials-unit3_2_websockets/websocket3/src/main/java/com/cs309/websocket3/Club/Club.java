package com.cs309.websocket3.Club;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "messages")
@Data
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String clubname;

    public Club() {

    }

    public Club(String name) {
        this.clubname = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClubName() {
        return clubname;
    }

    public void setClubName(String name) {
        this.clubname = name;
    }




}
