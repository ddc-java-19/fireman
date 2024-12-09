package edu.cnm.deepdive.fireman.model.domain;

import com.google.gson.annotations.Expose;

public record Move(@Expose Integer row, @Expose Integer column) {

}
