package com.example.myrestapi.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto implements Serializable {

    private Long id;

    @NotNull
    private String text;

    private Long userId;
}
