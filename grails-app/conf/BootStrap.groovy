import com.dropbyke.Role;
import com.dropbyke.User;
import com.dropbyke.UserRole;

class BootStrap {

	def grailsApplication

	def init = { servletContext ->

		User.withTransaction { status ->

			User user = User.findByUsername("admin")

			if(!user) {
				user = new User(username: "admin", password: grailsApplication.config.com.dropbyke.adminPassword)
				user.save()

				Role role = Role.findByAuthority("ROLE_ADMIN")

				if(!role) {
					role = new Role(authority: "ROLE_ADMIN")
					role.save();
				}

				UserRole.create(user, role)
			}
		}
	}
	def destroy = {
	}
}
