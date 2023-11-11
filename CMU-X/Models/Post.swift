// CMU-X -> model -> Post.swift

import Foundation
struct Comment: Codable, Identifiable {
    var commentid: Int
    var content: String
    var date: String
    var authorid: Int
    var likes: Int
    var postid: Int
}

struct Post: Codable, Identifiable {
    var postid: Int
    var title: String
    var content: String
    var date: String
    var authorid: Int
    var likes: Int
    var commentsCount: Int
    var comments: [Comment]

    var isFindTeammatePost: Bool
    var instructorName: Int
    var courseNumber: Int
    var semester: String
    var teamMembers: [String]
}

