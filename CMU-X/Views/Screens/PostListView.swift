import SwiftUI

struct PostListView: View {
    @State private var posts: [Post] = []
    @State private var isLoading = false
    @State private var errorMessage: String?

    var body: some View {
        NavigationView {
            List(posts) { post in
                NavigationLink(destination: PostView(post: post)) {
                    HStack {
                        VStack(alignment: .leading) {
                            Text(post.title).font(.headline)
                            Text(post.content).lineLimit(1)
                        }
                        Spacer()
                        Text("\(post.likes) likes")
                    }
                }
            }
            .navigationBarTitle("Community Posts")
            .onAppear {
                loadPosts()
            }
            .overlay {
                if isLoading {
                    ProgressView()
                }
            }
            .alert(isPresented: .constant(errorMessage != nil), content: {
                Alert(title: Text("Error"), message: Text(errorMessage ?? "Unknown error"), dismissButton: .default(Text("OK")))
            })
        }
    }
    
    func loadPosts() {
        isLoading = true
        APIService.fetchPosts { result in
            DispatchQueue.main.async {
                isLoading = false
                switch result {
                case .success(let posts):
                    self.posts = posts
                case .failure(let error):
                    self.errorMessage = error.localizedDescription
                }
            }
        }
    }
}

// Note: Update Post struct and preview provider based on your actual data and model
