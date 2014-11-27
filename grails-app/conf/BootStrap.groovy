import com.dropbyke.Bike;
import com.dropbyke.Card;
import com.dropbyke.Role;
import com.dropbyke.User;
import com.dropbyke.UserRole
import com.dropbyke.money.Account;

class BootStrap {

	def grailsApplication

	def init = { servletContext ->

		User.withTransaction { status ->

			User user = User.findByUsername("admin")

			if(!user) {
				user = new User(phone: "66666666666", username: "admin", password: grailsApplication.config.com.dropbyke.adminPassword, account: new Account(sum: 0L), name: "admin", email: "admin@dropbyke.com")
				user.save(flush: true)

				Role role = Role.findByAuthority("ROLE_ADMIN")

				if(!role) {
					role = new Role(authority: "ROLE_ADMIN")
					role.save();
				}

				UserRole.create(user, role)

				role = Role.findByAuthority("ROLE_USER")

				if(!role) {
					role = new Role(authority: "ROLE_USER")
					role.save();
				}

				UserRole.create(user, role)
			}

			user = User.findByPhone("11111111111")

			if(!user) {
				user = new User(phone: "11111111111", username: "11111111111", password: "11111111111", account: new Account(sum: 0L), name: "demo", email: "demo@demo.com")
				user.save(flush: true)

				Role role = Role.findByAuthority("ROLE_USER")

				if(!role) {
					role = new Role(authority: "ROLE_USER")
					role.save();
				}

				UserRole.create(user, role)
			}
		}

		grails.converters.JSON.registerObjectMarshaller(Bike, { Bike bike ->
			return [
				id : bike.id,
				sku : bike.sku,
				title : bike.title,
				rating : bike.rating,
				priceRate : bike.priceRate,
				lat: bike.lat,
				lng: bike.lng,
				address: bike.address,
				lockPassword: bike.lockPassword,
				messageFromLastUser: bike.messageFromLastUser ? bike.messageFromLastUser : null,
				lastRideId: bike.lastRideId,
				lastUserPhone: bike.lastUserPhone,
				active: bike.active,
				locked: bike.locked
			]
		})

		grails.converters.JSON.registerObjectMarshaller(Card, { Card card ->
			return [
				id : card.id,
				number : card.number,
				expire : card.expire,
				name : card.name,
				cvc : card.cvc,
			]
		})
	}
	def destroy = {
	}
}
