struct PostView: View {
    var post: Post

    var body: some View {
        HStack(alignment: .top, spacing: 10) {
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

                    // Comment icon with count
                    HStack(spacing: 2) {
                        Image(systemName: "bubble.right")
                            .resizable()
                            .frame(width: 15, height: 15)
                        Text("\(post.comments)")
                            .font(.subheadline)
                    }

                    // Like icon with count
                    HStack(spacing: 2) {
                        Image(systemName: "heart")
                            .resizable()
                            .frame(width: 15, height: 15)
                        Text("\(post.likes)")
                            .font(.subheadline)
                    }
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