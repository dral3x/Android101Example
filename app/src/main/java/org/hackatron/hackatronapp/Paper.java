package org.hackatron.hackatronapp;

/**
 * TODO
 */
public class Paper
{
    private final long _id;
    private String _title;
    private String _author;
    private String _notes;

    private long _votes;

    public Paper(long id)
    {
        this._id = id;
    }

    public String getTitle()
    {
        return this._title;
    }

    public void setTitle(String title)
    {
        this._title = title;
    }

    public String getNotes()
    {
        return this._notes;
    }

    public void setNotes(String notes)
    {
        this._notes = notes;
    }

    public void addVote()
    {
        this._votes++;
    }

    public void removeVote()
    {
        this._votes--;
    }
}
