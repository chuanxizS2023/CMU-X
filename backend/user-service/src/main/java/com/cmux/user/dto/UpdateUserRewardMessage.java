package com.cmux.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class UpdateUserRewardMessage implements Serializable {
    @NonNull
    private Long userId;
    @NonNull
    private String username;
    private Integer coinChangeAmount;
    private Integer pointChangeAmount;
}