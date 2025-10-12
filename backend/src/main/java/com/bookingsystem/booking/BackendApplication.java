package com.bookingsystem.booking;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bookingsystem.booking.room.data.RoomRepository;
import com.bookingsystem.booking.room.domain.entities.Room;
import com.bookingsystem.booking.room.domain.enums.RoomType;
import com.bookingsystem.booking.user.data.UserRepository;
import com.bookingsystem.booking.user.domain.entities.User;
import com.bookingsystem.booking.user.domain.enums.Role;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean CommandLineRunner seedUsers(UserRepository users, PasswordEncoder enc) { 
		return args -> { 
			users.findByEmailIgnoreCase("alice@example.com").orElseGet(() -> { 
				User u = new User(); 
				u.setEmail("alice@example.com"); 
				u.setUsername("alice"); u.setRole(Role.USER); 
				u.setPassword(enc.encode("P@ssw0rd!123")); 
			return users.save(u); 
			}); 
		}; 
	}

	@Bean
  	CommandLineRunner seedRooms(RoomRepository rooms) {
		return args -> {
			if (!rooms.existsByNameIgnoreCase("Meeting Room")) {
				Room r = new Room();
				r.setName("Meeting Room A");
				r.setType(RoomType.MEETING);
				r.setCapacity(10);
				r.setDescription("Cozy meeting room with whiteboard");
				rooms.save(r);
			}

			if (!rooms.existsByNameIgnoreCase("Music Room")) {
				Room r = new Room();
				r.setName("Conference Hall");
				r.setType(RoomType.MUSIC);
				r.setCapacity(5);
				r.setDescription("Guitar + Amp");
				rooms.save(r);
			}
    	};
	}
}
