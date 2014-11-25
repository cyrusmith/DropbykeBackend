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
				user = new User(username: "admin", password: grailsApplication.config.com.dropbyke.adminPassword, account: new Account(sum: 0L))
				user.save(flush: true)

				Role role = Role.findByAuthority("ROLE_ADMIN")

				if(!role) {
					role = new Role(authority: "ROLE_ADMIN")
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
