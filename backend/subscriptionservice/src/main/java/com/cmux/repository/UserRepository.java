package com.cmux.repository;


import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.cmux.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends Neo4jRepository<User, Long> {

    // Create a user with userId and name
    @Query("CREATE (u:User {userId: $userId, name: $name}) RETURN u")   
    User createUser(@Param("userId") Long userId, @Param("name") String name);

    // Get all users to whom the user with userId is subscribed
    @Query("MATCH (u:User)-[:SUBSCRIBED_TO]->(sub:User) WHERE ID(u) = $userId RETURN sub")
    List<User> findSubscriptionsByUserId(@Param("userId") Long userId);

    // Get all users who are subscribed to the user with userId
    @Query("MATCH (sub:User)-[:SUBSCRIBED_BY]->(u:User) WHERE ID(u) = $userId RETURN sub")
    List<User> findSubscribersByUserId(@Param("userId") Long userId);

    // Get all users who are subscribed to the user with userId and are also subscribed by the user with userId 
    @Query("MATCH (u:User)-[:SUBSCRIBED_TO]->(sub:User)-[:SUBSCRIBED_BY]->(u:User) WHERE ID(u) = $userId RETURN sub")
    List<User> findMutualSubscriptionsByUserId(@Param("userId") Long userId);

    // Add a subscription (current user subscribes to another user)
    @Query("MATCH (u:User), (other:User) WHERE ID(u) = $userId AND ID(other) = $otherUserId " +
           "MERGE (u)-[:SUBSCRIBED_TO]->(other)")
    void addSubscription(@Param("userId") Long userId, @Param("otherUserId") Long otherUserId);

    // Add a subscriber (another user subscribes to the current user)
    @Query("MATCH (u:User), (other:User) WHERE ID(u) = $userId AND ID(other) = $otherUserId " +
           "MERGE (other)-[:SUBSCRIBED_BY]->(u)")
    void addSubscriber(@Param("userId") Long userId, @Param("otherUserId") Long otherUserId);

    // Remove a subscription (current user unsubscribes from another user)
    @Query("MATCH (u:User)-[r:SUBSCRIBED_TO]->(other:User) WHERE ID(u) = $userId AND ID(other) = $otherUserId " +
           "DELETE r")
    void removeSubscription(@Param("userId") Long userId, @Param("otherUserId") Long otherUserId);

    // Remove a subscriber (another user unsubscribes from the current user)
    @Query("MATCH (u:User)<-[r:SUBSCRIBED_BY]-(other:User) WHERE ID(u) = $userId AND ID(other) = $otherUserId " +
           "DELETE r")
    void removeSubscriber(@Param("userId") Long userId, @Param("otherUserId") Long otherUserId);
}
