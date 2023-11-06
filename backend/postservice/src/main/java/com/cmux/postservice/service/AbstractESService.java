package com.cmux.postservice.service;


import com.cmux.postservice.handleException.IndexingException;
import co.elastic.clients.elasticsearch.ElasticsearchClient;


public abstract class AbstractESService<T> {
    protected final ElasticsearchClient elasticsearchClient;

    public AbstractESService(ElasticsearchClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }

    public void index(String index, String id, T document) {
        try {
            elasticsearchClient.index(i -> i.index(index).id(id).document(document));
            System.out.println(getClass().getSimpleName() + ": index: indexed document with id " + id);
        } catch (Exception e) {
            throw new IndexingException("Cannot index document: " + e.getMessage(), e);
        }
    }

}