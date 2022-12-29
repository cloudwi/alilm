package com.project.musinsastocknotificationbot.domain.product.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ProductSaveRequestDto {

    @NotNull(message = "품목 id는 필수 입니다.")
    private long id;
    @NotBlank(message = "사이즈 필수 입니다.")
    @Pattern(regexp = "[A-Z]+", message = "사이즈 형식에 맞지 않습니다.")
    @Size(max = 5, message = "사이즈는 0 ~ 4자 입니다.")
    private String size;

    public ProductSaveRequestDto(long id, String size) {
        this.id = id;
        this.size = size;
    }

    public long getId() {
        return id;
    }

    public String getSize() {
        return size;
    }
}
