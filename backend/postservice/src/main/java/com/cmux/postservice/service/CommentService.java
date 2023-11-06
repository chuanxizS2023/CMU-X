package com.cmux.postservice.service;

import org.springframework.stereotype.Service;
import com.cmux.postservice.model.Comment;
import com.cmux.postservice.dto.CommentDTO;
import com.cmux.postservice.model.CommentEvents;
import java.util.NoSuchElementException;
import com.cmux.postservice.converter.CommentConverter;
import com.cmux.postservice.repository.CommentRepository;
import org.springframework.context.ApplicationEventPublisher;
import jakarta.transaction.Transactional;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

@Service
public class CommentService extends AbstractESService<Comment> {

    private CommentRepository commentRepository;
    private CommentConverter commentConverter;
    private final ApplicationEventPublisher publisher;

    public CommentService(CommentRepository commentRepository, 
                          CommentConverter commentConverter,
                          ApplicationEventPublisher publisher
                          ,ElasticsearchClient elasticsearchClient) {
        super(elasticsearchClient);
        this.commentRepository = commentRepository;
        this.commentConverter = commentConverter;
        this.publisher = publisher;
        
    }


    public CommentDTO saveComment(CommentDTO commentdto){
        Comment comment = commentConverter.convertDTOToEntity(commentdto);
        
        commentRepository.save(comment);

        this.publisher.publishEvent(new CommentEvents.Created(comment));

        return commentConverter.convertEntityToDTO(comment);
    }

    @Transactional
    public Optional<CommentDTO> getCommentById(long commentid){
        Optional<Comment> comment = commentRepository.findById(commentid);
        if (comment.isPresent()) {
            CommentDTO commentdto = commentConverter.convertEntityToDTO(comment.get());
            return Optional.of(commentdto);
        }else {
            throw new NoSuchElementException("Comment not found for id: " + commentid);
        }
    }


}
