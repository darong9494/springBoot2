package com.tenco.csr_blog_v1.reply;

import com.tenco.csr_blog_v1.user.User;

public class ReplyResponse {
    public record DTO(
            Integer id,
            String comment,
            Integer userId,
            Integer boardId,
            String username,
            Boolean isOwner
    ) {
       public DTO(Reply reply, User sessionUser) {
           this(
                   reply.getId(),
                   reply.getComment(),
                   reply.getUser().getId(),
                   reply.getBoard().getId(),
                   sessionUser.getUsername(),
                   reply.getUser().getId().equals(sessionUser.getId())
           );
       }
    }
}
