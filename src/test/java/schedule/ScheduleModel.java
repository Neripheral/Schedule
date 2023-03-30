package schedule;

class ScheduleModel {
    public ScheduleModel(int age, String name, String surname) {
        this.age = age;
        this.name = name;
        this.surname = surname;
    }

    public static ScheduleModel getFreshModel(){
        return new ScheduleModel(20, "John", "Smith");
    }

    public int age;
    public String name;
    public String surname;
}
