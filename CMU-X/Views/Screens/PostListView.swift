import SwiftUI

struct PostListView: View {
    @State var posts: [Post] = [] // Assuming Post is a model conforming to Identifiable

    var body: some View {
        NavigationView {
            List(posts) { post in
                PostView(post: post)
            }
            .navigationTitle("Community Posts")
        }
    }
}

struct PostListView_Previews: PreviewProvider {
    static var previews: some View {
        // Generate some example data for the preview
        PostListView(posts: [
            Post(id: "1", username: "User1", content: "Great course on system design!", timestamp: "3h ago", likes: 120, comments: 45),
            Post(id: "2", username: "User2", content: "Loved the latest update!", timestamp: "1h ago", likes: 85, comments: 30)
        ])
    }
}
