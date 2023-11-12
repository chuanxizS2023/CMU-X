class APIService {
    static let baseUrlString = "localhost:9000"  // Replace with your actual base URL

    // MARK: Comment API Endpoints
    static func createComment(comment: Comment, completion: @escaping (Result<Comment, Error>) -> Void) {
        let url = URL(string: "\(baseUrlString)/comments")!
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")

        do {
            let jsonData = try JSONEncoder().encode(comment)
            request.httpBody = jsonData
        } catch {
            completion(.failure(error))
            return
        }

        URLSession.shared.dataTask(with: request) { data, response, error in
            // Handle the response or error
        }.resume()
    }
    
    static func fetchPosts(completion: @escaping (Result<[Post], Error>) -> Void) {
        let request = URLRequest(url: baseUrl.appendingPathComponent("/posts"))
        
        URLSession.shared.dataTask(with: request) { data, response, error in
            if let error = error {
                completion(.failure(error))
                return
            }
            guard let data = data else {
                completion(.failure(NSError(domain: "", code: -1, userInfo: nil)))
                return
            }
            do {
                let posts = try JSONDecoder().decode([Post].self, from: data)
                completion(.success(posts))
            } catch {
                completion(.failure(error))
            }
        }.resume()
    }

    // MARK: Fetch Comments for Post
    static func fetchComments(for postId: String, completion: @escaping (Result<[Comment], Error>) -> Void) {
        let request = URLRequest(url: baseUrl.appendingPathComponent("/posts/\(postId)/comments"))
        
        URLSession.shared.dataTask(with: request) { data, response, error in
            if let error = error {
                completion(.failure(error))
                return
            }
            guard let data = data else {
                completion(.failure(NSError(domain: "", code: -1, userInfo: nil)))
                return
            }
            do {
                let comments = try JSONDecoder().decode([Comment].self, from: data)
                completion(.success(comments))
            } catch {
                completion(.failure(error))
            }
        }.resume()
    }

    
    static func updateComment(commentId: Int, updatedComment: Comment, completion: @escaping (Result<Comment, Error>) -> Void) {
        let url = URL(string: "\(baseUrlString)/comments/\(commentId)")!
        var request = URLRequest(url: url)
        request.httpMethod = "PUT"
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")

        do {
            let jsonData = try JSONEncoder().encode(updatedComment)
            request.httpBody = jsonData
        } catch {
            completion(.failure(error))
            return
        }

        URLSession.shared.dataTask(with: request) { data, response, error in
            // Handle the response or error
        }.resume()
    }

    static func deleteComment(commentId: Int, completion: @escaping (Result<Bool, Error>) -> Void) {
        let url = URL(string: "\(baseUrlString)/comments/\(commentId)")!
        var request = URLRequest(url: url)
        request.httpMethod = "DELETE"

        URLSession.shared.dataTask(with: request) { data, response, error in
            if let error = error {
                completion(.failure(error))
                return
            }
            // Assuming deletion success does not return any content
            completion(.success(true))
        }.resume()
    }

    static func createPost(post: Post, completion: @escaping (Result<Post, Error>) -> Void) {
        let url = URL(string: "\(baseUrlString)/posts")!  // Adjust the endpoint as per your backend URL structure
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")

        do {
            let jsonData = try JSONEncoder().encode(post)
            request.httpBody = jsonData
        } catch {
            completion(.failure(error))
            return
        }

        URLSession.shared.dataTask(with: request) { data, response, error in
            // Handle the response or error
        }.resume()
    }
}