package com.cmux.postservice.service;

import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmux.postservice.model.CommunityPost;
import com.cmux.postservice.model.Comment;
import com.cmux.postservice.handleException.IndexingException;

import org.springframework.web.bind.MethodArgumentNotValidException;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClientBuilder;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;

@Service
public class ElasticsearchService {

    private final RestClient restClient;
    private final ElasticsearchTransport elasticsearchTransport;
    private final ElasticsearchClient elasticsearchClient;



    @Autowired
    public ElasticsearchService() {
        final BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
            new UsernamePasswordCredentials("elastic", "2JeXn31HFDxoD3iMkX5v"));

        this.restClient = RestClient.builder(
            new HttpHost("elasticsearch", 9200)
        ).setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
            @Override
            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            }
        })
        .build();

        this.elasticsearchTransport = new RestClientTransport(this.restClient, new JacksonJsonpMapper());
    
        this.elasticsearchClient = new ElasticsearchClient(this.elasticsearchTransport);

    }
    
    public void indexPost(CommunityPost post) {
        try {
            IndexResponse response = elasticsearchClient.index(i -> i
                .index("communitypost")
                .id(String.valueOf(post.getCommunityPostid()))
                .document(post)
            );
            System.out.println("ElasticsearchService: indexPost: indexed post");
        } catch (Exception e) {
            // Handle the exception
            throw new IndexingException("Cannot index post: " + e.getMessage(), e);        }
    }

    public void indexComment(Comment comment){
        try{
            IndexResponse response = elasticsearchClient.index(i -> i
                .index("comment")
                .id(String.valueOf(comment.getCommentid()))
                .document(comment)
            );
        } catch (Exception e) {
            // Handle the exception
            throw new IndexingException("Cannot index comment: " + e.getMessage(), e);
        }
    }


}
