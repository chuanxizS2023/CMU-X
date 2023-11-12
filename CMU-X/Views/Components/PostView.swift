import SwiftUI

struct PostView: View {
    var post: Post
    @State private var comments: [Comment] = []
    @State private var isLoading = false
    @State private var errorMessage: String?

    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 10) {
                Text(post.title).font(.title)
                Text(post.content).font(.body)
                Divider()
                if isLoading {
                    ProgressView().padding()
                } else {
                    ForEach(comments) { comment in
                        Text(comment.content)
                    }
                }
            }
            .padding()
            .onAppear {
                loadComments()
            }
            .navigationBarTitle(Text(post.title), displayMode: .inline)
            .alert(isPresented: .constant(errorMessage != nil), content: {
                Alert(title: Text("Error"), message: Text(errorMessage ?? "Unknown error"), dismissButton: .default(Text("OK")))
            })
        }
    }

    private func loadComments() {
        isLoading = true
        APIService.fetchComments(for: post.id) { result in
            DispatchQueue.main.async {
                isLoading = false
                switch result {
                case .success(let fetchedComments):
                    self.comments = fetchedComments
                case .failure(let error):
                    self.errorMessage = error.localizedDescription
                }
            }
        }
    }
}




struct PostView_Previews: PreviewProvider {
    static var previews: some View {
        PostView(post: Post(id: "1", username: "username1", content: "I love Professor Cecile and her classes. Feel like she has put so much effort on designing these courses. And I got A on her FSE. It was such an unforgettable experience with her and Professor Hakan. I really enjoy taking FSE with them. Hope you all had a great time!", timestamp: "3h", likes: 1, comments: 10))
    }
}
