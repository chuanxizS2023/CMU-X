import SwiftUI

struct PostListView: View {
    @State var posts = [
        Post(username: "Harris Potter", content: "I love Professor Cecile and her classes...", timestamp: "14h"),Post(username: "Tom Dai", content: "Where is Hakan", timestamp: "14min")
        // Add more dummy `Post` data as needed for the preview
    ]

    var body: some View {
        NavigationView {
            List(posts) { post in
                PostView(post: post)
            }
            .navigationTitle("Community Posts")
            .listStyle(PlainListStyle()) // Use `PlainListStyle` for a cleaner look
        }
    }
}

struct PostListView_Previews: PreviewProvider {
    static var previews: some View {
        PostListView()
    }
}
