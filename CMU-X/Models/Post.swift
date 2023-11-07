// CMU-X -> model -> Post.swift

import Foundation
struct Post: Identifiable {
    let id: String
    let username: String
    let content: String
    let timestamp: String
    var likes: Int // Added property for likes count
    var comments: Int // Added property for comments count
}

