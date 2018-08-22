package me.kodingking.kodax.api.zyntax.objects;

import com.google.gson.Gson;

public class StaffGroup {
    public String groupName;
    public int hierachyLevel;
    public String color;
    public StaffMember[] members;
    public static class StaffMember {
        public String uuid;
        public String nickname;
    }
    @Override
    public String toString() {
        return "[" + new Gson().toJson(this) + "]";
    }
}
