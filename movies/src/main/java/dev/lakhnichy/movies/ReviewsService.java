package dev.lakhnichy.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class ReviewsService {
    @Autowired
    private ReviewsRepository reviewsRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    public Reviews createReview(String reviewBody, String imdbId){
        Reviews review = reviewsRepository.insert(new Reviews(reviewBody));

        mongoTemplate.update(Movie.class).matching(Criteria.where("imdbId").is(imdbId))
                .apply(new Update().push("reviewIds").value(review))
                .first();
        return review;
    }
}
