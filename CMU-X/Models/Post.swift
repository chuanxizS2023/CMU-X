// CMU-X -> model -> Post.swift

import Foundation

struct Post: Identifiable {
    let id = UUID()
    let username: String
    let content: String
    let timestamp: String // For simplicity, let's keep it as a String
    // You would have other properties like userImage, likes, comments etc.
}
