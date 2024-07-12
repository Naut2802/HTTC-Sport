package com.fpoly.httc_sport.dto.request;

import java.util.Set;

public record AuthorizeUserRequest(Set<String> roles) { }
