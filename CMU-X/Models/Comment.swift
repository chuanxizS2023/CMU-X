import Foundation
struct Comment: Codable, Identifiable {
    var commentid: Int
    var content: String
    var date: String
    var authorid: Int
    var likes: Int
    var postid: Int
}
