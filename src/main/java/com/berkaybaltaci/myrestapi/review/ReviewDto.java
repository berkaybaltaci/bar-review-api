package com.berkaybaltaci.myrestapi.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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

    @Min(1)
    @Max(5)
    private int rating;
}
