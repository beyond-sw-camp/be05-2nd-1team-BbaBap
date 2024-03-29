package com.encore.bbabap.api.board.response;

import com.encore.bbabap.api.comment.response.CommentResponseDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardResponseDTO {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime registeredAt;
    private LocalDateTime updatedAt;
    private Boolean deletedYn;
    private String email;
    private List<CommentResponseDTO> comments;
}