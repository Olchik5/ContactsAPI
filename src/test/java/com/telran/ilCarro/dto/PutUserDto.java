package com.telran.ilCarro.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class PutUserDto {
    String first_name;
    String second_name;
}
