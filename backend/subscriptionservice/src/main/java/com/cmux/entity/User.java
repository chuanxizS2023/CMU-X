package com.cmux.entity;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node(labels = {"Student"})
public class User {

	@Id
	@GeneratedValue
	private Long id;

    private Long CMUid;

	private String name;

	public Long getId() {
		return id;
	}

    public Long getCMUid() {
        return CMUid;
    }

    public void setCMUid(Long CMUid) {
        this.CMUid = CMUid;
    }

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
