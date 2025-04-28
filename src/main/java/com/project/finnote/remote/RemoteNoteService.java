package com.project.finnote.remote;

import com.fasterxml.jackson.core.type.TypeReference;
import com.project.finnote.entity.Notes;
import com.project.finnote.interfaces.INoteService;

import java.util.List;

public class RemoteNoteService implements INoteService {
    private final RemoteServiceHelper helper = new RemoteServiceHelper();

    @Override
    public List<Notes> findAll() {
        try {
            String json = helper.sendCommand("GET_NOTES");
            return helper.mapper().readValue(json, new TypeReference<List<Notes>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Remote fetch of notes failed", e);
        }
    }

    @Override
    public Notes addNote(String title, String content, int categoryId) {
        try {
            String cmd = String.join("|", "ADD_NOTE", title, content, String.valueOf(categoryId));
            String json = helper.sendCommand(cmd);
            return helper.mapper().readValue(json, Notes.class);
        } catch (Exception e) {
            throw new RuntimeException("Remote add note failed", e);
        }
    }

}
