// CMU-X -> view -> component -> PostView.swift

import SwiftUI

struct PostView: View {
    var post: Post

    var body: some View {
        HStack(spacing: 10) {
            Image(systemName: "person.circle.fill") // Placeholder for the profile image
                .resizable()
                .scaledToFit()
                .frame(width: 50, height: 50)
                .clipShape(Circle())
                .overlay(Circle().stroke(Color.gray, lineWidth: 1))

            VStack(alignment: .leading, spacing: 4) {
                Text(post.username)
                    .fontWeight(.semibold)

                Text(post.content)
                    .font(.body)
                    .lineLimit(4)

                HStack {
                    Text(post.timestamp)
                        .font(.subheadline)
                        .foregroundColor(.gray)
                    Spacer()
                    // Add more icons or interactions here if needed.
                }
            }
        }
        .padding()
        .background(Color.white)
        .cornerRadius(10)
        .shadow(radius: 1)
    }
}

struct PostView_Previews: PreviewProvider {
    static var previews: some View {
        PostView(post: Post(username: "Username", content: "I love Professor Cecile and her classes. Feel like she has put so much effort on designing these courses. And I got A on her FSE. It was such an unforgettable experience with her and Professor Hakan. I really enjoy taking FSE with them. Hope you all had a great time!", timestamp: "3h"))
    }
}
