
public class Customer {
	private String name;
	private String phone;
	private String password;
	private String filePath;
	private String desc;
	private String courses;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getCourses() {
		return courses;
	}
	public void setCourses(String courses) {
		this.courses = courses;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public Customer(String name, String phone, String password, String filePath, String desc, String courses) {
		super();
		this.name = name;
		this.phone = phone;
		this.password = password;
		this.filePath = filePath;
		this.courses = courses;
		this.desc = desc;
	}
	
	public Customer() {
		
	}
	
}
