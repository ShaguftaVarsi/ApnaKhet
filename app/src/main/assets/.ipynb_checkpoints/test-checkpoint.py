import streamlit as st
import matplotlib.pyplot as plt
import numpy as np
import tensorflow as tf
from keras import utils
import keras
from PIL import Image
from googleapiclient.discovery import build
from  streamlit_player import st_player
# Replace with your YouTube Data API key
api_key = 'AIzaSyAacWubF2RNwzEEfoO1Hl6nKN2d5fqp-l0'

# Build the YouTube API client
youtube = build('youtube', 'v3', developerKey=api_key)

def search_youtube_videos(keyword, max_results=2):
    
    request = youtube.search().list(
        q=keyword,
        part='snippet',
        type='video',
        maxResults=max_results
    )
    response = request.execute()

    # Extract video links
    video_links = []
    thumbnail_links=[]
    for item in response['items']:
        video_id = item['id']['videoId']
        video_url = f"https://www.youtube.com/watch?v={video_id}"
        thumbnail_url = item['snippet']['thumbnails']['high']['url']
        video_links.append(video_url)
        thumbnail_links.append(thumbnail_url)
    
    return video_links,thumbnail_links


model = keras.saving.load_model('models/a.keras')
classes = ['Cashew anthracnose',
 'Cashew gumosis',
 'Cashew healthy',
 'Cashew leaf miner',
 'Cashew red rust']



def preprocess_img(img):
    # op_img = Image.open(img_path)
    img2arr = utils.img_to_array(img)
    img2arr = tf.expand_dims(img2arr,0)
    return img2arr

def predict_result(model, predict):
    pred = model.predict(predict)
    predicted_class = classes[np.argmax(pred[0])]
    confidence=round(100 * (np.max(pred[0])),2)
    return predicted_class,confidence



st.set_page_config(
    page_title='Plant Disease Prediction',
    page_icon="🌳",
)

st.title("Plant Disease Prediction")



# Upload image
uploaded_image = st.file_uploader("Choose an image...", type=["jpg", "png", "jpeg"])

if uploaded_image is not None:
   
    image = Image.open(uploaded_image)
    st.image(image, caption='Uploaded Image', use_column_width=True,width=100)

    # Predict the image
    st.write("Classifying...")
    image = preprocess_img(image)
    prediction, confidence = predict_result(model,image)
    
    st.header(prediction)
    st.write("Confidence: " ,confidence)
    keyword = prediction
    videos,thumbnails = search_youtube_videos(keyword)

    st.write("YouTube Videos:")
    for video in videos:
        st_player(video)


    if prediction == 'Cashew anthracnose':
        st.success("""Symptoms: Dark, sunken lesions on leaves, twigs, and fruits.
Leaves may become distorted and drop prematurely.
Fruits may develop black spots and eventually rot.""")
        st.success("""Chemical Treatment: Fungicides: Use copper-based fungicides like copper oxychloride or systemic fungicides such as carbendazim or mancozeb.
Application: Begin treatment at the first sign of infection. Repeat every 7-10 days, especially during wet and humid conditions.""")
        st.success("""Organic Treatment: Neem Oil: Regular application of neem oil can help manage the spread.
Baking Soda Solution: A mixture of 1 tablespoon of baking soda, 1 liter of water, and a few drops of liquid soap can be sprayed on affected areas to prevent the spread.
Precautions:""")
        st.success("""Precautions: Prune and remove infected plant parts to prevent the spread.Ensure proper spacing and good air circulation within the plantation.
Avoid overhead watering to reduce humidity levels around the plants.""")
        
    
    if prediction == 'Cashew gumosis':
        st.success("""Symptoms: Exudation of gum from the bark, especially near the base of the trunk.Cracks on the bark with a gummy substance seeping out.
Tree vigor declines over time, leading to reduced fruit production.""")
        st.success("""Chemical Treatment: Fungicides: Apply systemic fungicides like fosetyl-Al or metalaxyl around the base of the trunk.
Trunk Injection: In severe cases, fungicides can be injected directly into the trunk to halt disease progression.""")
        st.success("""Organic Treatment: Trunk Paste: Apply a paste made of lime and copper sulfate around the affected area.
Neem Oil: Regular application around the base can act as a preventive measure""")
        st.success("""Precautions:  Avoid injuries to the tree bark, as this can be an entry point for pathogens.
Ensure proper drainage to avoid waterlogging, which can predispose the tree to gumosis.
Keep the base of the tree free from weeds and debris.""")

    if prediction == 'Cashew healthy':
        st.success("""Regular Monitoring: Regularly inspect plants for early signs of diseases or pests.
Balanced Fertilization: Ensure the plant receives adequate nutrients for optimal growth and resistance to diseases.
Good Agricultural Practices: Maintain proper spacing, irrigation, and pruning practices to keep the plant healthy.""")

    if prediction == "Cashew leaf miner":
        st.success("""Symptoms: Tunnels or mines in the leaves caused by larvae feeding within the leaf tissue.
Affected leaves may curl, become distorted, and fall off prematurely.""")
        st.success("""Chemical Treatment: Fungicides: Apply systemic insecticides like imidacloprid or thiamethoxam.
Application: Spray when the first signs of leaf mining are observed. Repeat applications as necessary.""")
        st.success("""Organic Treatment: Neem Oil: Neem oil can help repel and control leaf miner infestations when applied regularly.
Biological Control: Introduce natural predators like parasitic wasps that target leaf miner larvae.""")
        st.success("""Precautions: Remove and destroy heavily infested leaves to prevent the spread.Encourage natural predators by maintaining a healthy ecosystem around the farm.
Monitor the plants regularly, especially during the growing season, to catch infestations early.""")


    if prediction == "Cashew red rust":
        st.success("""Symptoms: Orange-red rust-colored pustules on the underside of leaves.Severe infections can lead to leaf drop and reduced photosynthesis, affecting overall plant health.""")
        st.success("""Chemical Treatment: Fungicides: Use sulfur-based fungicides or copper oxychloride to control the spread of rust.
Application: Apply at the first sign of symptoms and continue as per recommended intervals.""")
        st.success("""Organic Treatment: Neem Oil: Regular spraying with neem oil can help manage red rust.Garlic Spray: A homemade garlic spray (blend garlic with water and strain) can be applied as a preventive measure.""")
        st.success("""Precautions: Remove and destroy infected leaves to reduce the spread of the disease.Ensure proper spacing and air circulation within the plantation.Avoid overhead watering to reduce leaf wetness, which can encourage the spread of rust.""")
# if __name__ == '__main__':
#     st.run()