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

    static func fetchComment(commentId: Int, completion: @escaping (Result<Comment, Error>) -> Void) {
        let url = URL(string: "\(baseUrlString)/comments/\(commentId)")!
        var request = URLRequest(url: url)
        request.httpMethod = "GET"

        URLSession.shared.dataTask(with: request) { data, response, error in
            if let error = error {
                completion(.failure(error))
                return
            }
            
            guard let data = data else {
                completion(.failure(NSError(domain: "", code: -1, userInfo: [NSLocalizedDescriptionKey: "No data received"])))
                return
            }

            do {
                let comment = try JSONDecoder().decode(Comment.self, from: data)
                completion(.success(comment))
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