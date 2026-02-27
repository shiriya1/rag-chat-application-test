package com.ragchat.storage.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSessionRequest {
    private String title;
    private Boolean favorite;
}
