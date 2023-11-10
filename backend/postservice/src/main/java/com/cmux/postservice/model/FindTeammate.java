// package com.cmux.postservice.model;

// import lombok.Data;
// import lombok.EqualsAndHashCode;
// import lombok.ToString;

// import jakarta.persistence.*;
// import java.util.List;

// @Data
// @Entity
// @EqualsAndHashCode(callSuper = true)
// @ToString(callSuper = true)
// @Table(name = "find_teammate_post")
// public class FindTeammate extends CommunityPost {
    
//     private String courseNumber; 
//     private String instructorName; 
//     private String semester;
    
//     @ElementCollection
//     @CollectionTable(name = "team_list", joinColumns = @JoinColumn(name = "find_teammate_post_id"))
//     @Column(name = "team_name")
//     private List<String> teamList; 

// }
